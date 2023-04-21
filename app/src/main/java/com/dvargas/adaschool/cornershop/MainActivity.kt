package com.dvargas.adaschool.cornershop

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dvargas.adaschool.cornershop.dataLayer.dto.auth.AuthDto
import com.dvargas.adaschool.cornershop.domain.services.AuthService
import com.dvargas.adaschool.cornershop.domain.services.HealthService
import com.dvargas.adaschool.cornershop.domain.services.UserService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var healthService: HealthService

    @Inject
    lateinit var authService: AuthService

    @Inject
    lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login()
        generateDataUser()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun login() {
        GlobalScope.launch {
            Log.d("Developer", "Trying login")
            val authDto = AuthDto("ada2@mail.com", "Ada#sCh0ol*")
            val response = authService.getAuth(authDto)
            if (response.isSuccessful) {
                runOnUiThread {
                    val textToken = findViewById<TextView>(R.id.token)
                    textToken.append(response.body()?.token)
                }
            } else {
                println(response.errorBody())
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun generateDataUser() {
        val button = findViewById<Button>(R.id.buttonGenerateUserData)
        button.setOnClickListener {
            GlobalScope.launch {
                val response = userService.getUserById("64233a1b9d2a5d3f69043fc0")
                if (response.isSuccessful) {
                    runOnUiThread {
                        val textName = findViewById<TextView>(R.id.name)
                        val textLastName = findViewById<TextView>(R.id.lastName)
                        val textEmail = findViewById<TextView>(R.id.email)
                        val textPassword = findViewById<TextView>(R.id.password)
                        textName.append(response.body()?.name)
                        textLastName.append(response.body()?.lastName)
                    }
                } else {
                    println(response.errorBody())
                }
            }
        }
    }
}