package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginSuccessfulActivity : AppCompatActivity() {

    private lateinit var welcomeTextView: TextView
    private lateinit var exitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_successful)

        welcomeTextView = findViewById(R.id.welcomeTextView)
        exitButton = findViewById(R.id.exitButton)

        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        val message = "Welcome $username!"
        welcomeTextView.text = message

        exitButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("username",username)
            intent.putExtra("password",password)
            startActivity(intent)
        }
    }
}