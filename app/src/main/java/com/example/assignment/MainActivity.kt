package com.example.assignment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*

var loginAttempts = 0

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var usernameInLogin: EditText
    private lateinit var passwordInLogin: EditText
    private val REQUEST_CODE = 1
    private var countLog = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)


        loginButton.setOnClickListener {
            usernameInLogin=findViewById(R.id.usernameInLogin)
            passwordInLogin=findViewById(R.id.passwordInLogin)
            val usernameText = usernameInLogin.text.toString()
            val passwordText = passwordInLogin.text.toString()

            Log.d("MainActivity","loginAttempts:$loginAttempts")
            val username = getUsernameFromIntent()
            val password = getPasswordFromIntent()
            Log.d("MainActivity", "Username: $username , Input: $usernameText")
            Log.d("MainActivity", "Password: $password , Input: $passwordText")

            if (username == usernameText && password == passwordText) {
                val message = "Welcome $username!"
                val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
                toast.show()

                val intent = Intent(this, LoginSuccessfulActivity::class.java)
                intent.putExtra("username", username)
                intent.putExtra("password",password)
                startActivity(intent)
            }else if (countLog>3){
                val warn = "Attempt too many times. Wait 30 seconds and try again"
                val toast = Toast.makeText(this, warn, Toast.LENGTH_LONG)
                toast.show()
                loginButton.isEnabled=false
                Log.d("MainActivity",loginButton.isEnabled.toString())

                val countDownTimer = object : CountDownTimer(30000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        // Do nothing
                    }
                    override fun onFinish() {
                        loginButton.isEnabled = true
//                            isLoginButtonDisabled = false
//                            loginAttempts = 0
                    }
                }
                countDownTimer.start()
                countLog=0
            }
            // 如果是第一次尝试，提示用户先注册
            if (loginAttempts < 1) {
                Toast.makeText(this, "Please register first...", Toast.LENGTH_SHORT).show()
            }
            else {
                //loginButton.isEnabled = false
                val message = "Incorrect username or password. Please try again."
                val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
                toast.show()
                countLog++
                Log.d("MainActivity", "Login attempts: $loginAttempts")
            }
            loginAttempts++

        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val username = data?.getStringExtra("username")
            val password = data?.getStringExtra("password")

        }
    }

    private fun getUsernameFromIntent(): String {
        val username = intent.getStringExtra("username")
        return username.toString()
    }

    private fun getPasswordFromIntent(): String {
        val password = intent.getStringExtra("password")
//        return password ?: ""
        return password.toString()
    }
}