package com.dvargas.adaschool.cornershop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dvargas.adaschool.cornershop.dataLayer.dto.local.api.LoginDto
import com.dvargas.adaschool.cornershop.databinding.ActivityLoginBinding
import com.dvargas.adaschool.cornershop.domain.services.local.LoginService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    @Named("local")
    lateinit var loginService: LoginService

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        startUp()
    }

    private fun startUp() {
        binding.signInButton.setOnClickListener {
            login()
        }
        binding.signUpButton.setOnClickListener {
            register()
        }
    }

    private fun login() {
        val email = binding.loginEmailAddress.text.toString()
        val password = binding.loginPassword.text.toString()
        if (validateLoginEntry(email, password)) {
            validateUser(email, password)
        }
    }

    private fun register() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Sign Up")
        alertDialog.setMessage("If you don't have an account, you can register.")
        alertDialog.setPositiveButton("Register") { _, _ ->
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        alertDialog.setNegativeButton("I have an account") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    private fun validateLoginEntry(email: String, password: String): Boolean {
        val alertDialog = AlertDialog.Builder(this)
        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
        val passwordRegex =
            Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$")
        if (!emailRegex.matches(email)) {
            alertDialog.setTitle("Email not valid")
            alertDialog.setMessage("Please insert a valid email")
            alertDialog.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()
            return false
        } else if (!passwordRegex.matches(password)) {
            alertDialog.setTitle("Password not valid.")
            alertDialog.setMessage(
                "Very short password. Use at least 1 uppercase, 1 lowercase, " + "1 number, and 1 special character. Minimum length = 8"
            )
            alertDialog.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()
            return false
        }
        return true
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun validateUser(email: String, password: String) {
        val loginDto = LoginDto(email, password)
        val alertDialog = AlertDialog.Builder(this)
        GlobalScope.launch {
            val loginResponse = loginService.getLogin(loginDto)
            if (loginResponse.isSuccessful) {
                when (loginResponse.body()?.codeResponse) {
                    200 -> {
                        val currentDateTime = LocalDateTime.now()
                        val dateFormatted = currentDateTime.format(DateTimeFormatter.ISO_DATE)
                        val sharedPreferences =
                            getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("email", email)
                        editor.putString("date", dateFormatted)
                        editor.apply()
                        runOnUiThread {
                            alertDialog.setTitle("Sign In")
                            alertDialog.setMessage(loginResponse.body()?.response)
                            alertDialog.setPositiveButton("OK") { _, _ ->
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                            alertDialog.show()
                        }
                    }
                    201 -> {
                        runOnUiThread {
                            alertDialog.setTitle("Sign In")
                            alertDialog.setMessage(loginResponse.body()?.response)
                            alertDialog.setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            alertDialog.show()
                        }
                    }
                    else -> {
                        runOnUiThread {
                            alertDialog.setTitle("Sign In")
                            alertDialog.setMessage(loginResponse.body()?.response)
                            alertDialog.setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            alertDialog.show()
                        }
                    }
                }
            }
        }
    }
}