package com.example.lms_course_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lms_course_app.databinding.ActivityLoginPageBinding

class LoginPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Настройка ViewBinding
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Переход обратно на WelcomePage
        binding.buttonBackToWelcome.setOnClickListener {
            val intent = Intent(this, WelcomePageActivity::class.java)
            startActivity(intent)
        }
    }
}