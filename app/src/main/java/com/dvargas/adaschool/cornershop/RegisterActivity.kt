package com.dvargas.adaschool.cornershop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dvargas.adaschool.cornershop.dataLayer.dto.local.api.UserDto
import com.dvargas.adaschool.cornershop.databinding.ActivityRegisterBinding
import com.dvargas.adaschool.cornershop.domain.services.local.LoginService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    @Inject
    @Named("local")
    lateinit var loginService: LoginService

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        startUp()
    }

    private fun startUp() {
        binding.signUpButton.setOnClickListener {
            val name = binding.registerName.text.toString()
            val lastName = binding.registerLastName.text.toString()
            val email = binding.registerEmail.text.toString()
            val confirmEmail = binding.registerConfirmEmail.text.toString()
            val password = binding.registerPassword.text.toString()
            val confirmPassword = binding.registerConfirmPassword.text.toString()
            val cellphone = binding.registerPhone.text.toString()
            if (isEmailValid(email, confirmEmail)) {
                if (isPasswordValid(password, confirmPassword)){
                    val userDto = UserDto(name, lastName, email, password, cellphone)
                    isUserRegister(userDto)
                }
            }
        }
    }

    private fun isEmailValid(email: String, confirm: String): Boolean {
        val alertDialog = AlertDialog.Builder(this)
        if (email != confirm) {
            alertDialog.setTitle("Email doesn't match")
            alertDialog.setMessage("Email and email confirm doesn't are identical")
            alertDialog.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()
            return false
        }
        return true
    }

    private fun isPasswordValid(password: String, confirm: String): Boolean {
        val alertDialog = AlertDialog.Builder(this)
        if (password != confirm) {
            alertDialog.setTitle("Password doesn't match")
            alertDialog.setMessage("Password and password confirm doesn't are identical")
            alertDialog.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()
            return false
        }
        return true
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun isUserRegister(userDto: UserDto) {
        val alertDialog = AlertDialog.Builder(this)
        GlobalScope.launch {
            val response = loginService.isUserRegister(userDto.email)
            if (response.isSuccessful) {
                if (response.body() == true) {
                    runOnUiThread {
                        alertDialog.setTitle("User already registered.")
                        alertDialog.setMessage("The email is already registered here. Please log in.")
                        alertDialog.setPositiveButton("OK") { _, _ ->
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)

                        }
                        alertDialog.show()
                    }
                } else {
                    registerUser(userDto)
                }
            }
        }
    }

    private suspend fun registerUser(userDto: UserDto) {
        val alertDialog = AlertDialog.Builder(this)
        val response = loginService.registerUser(userDto)
        if (response.isSuccessful){
            runOnUiThread {
                alertDialog.setTitle("User registered.")
                alertDialog.setMessage("Please log in.")
                alertDialog.setPositiveButton("OK") { _, _ ->
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                alertDialog.show()
            }
        } else {
            runOnUiThread {
                alertDialog.setTitle("User not registered.")
                alertDialog.setMessage("Please try again-")
                alertDialog.setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
        }
    }
}