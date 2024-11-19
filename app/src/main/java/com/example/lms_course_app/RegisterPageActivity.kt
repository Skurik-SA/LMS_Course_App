package com.example.lms_course_app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lms_course_app.databinding.ActivityRegisterPageBinding
import com.example.lms_course_app.utils.TextHighlighter

class RegisterPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Настройка ViewBinding
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textView = findViewById<TextView>(R.id.privacyPolicyText)
        val fullText = getString(R.string.privacy_policy)

        val phrasesToHighlight = listOf(
            getString(R.string.privacy_policy_key),
            getString(R.string.terms_of_service_key)
        )

        // Используем TextHighlighter
        val highlighter = TextHighlighter(this)
        val highlightedText = highlighter.highlightText(fullText, phrasesToHighlight, R.color.purple_700)

        // Устанавливаем текст с выделениями
        textView.text = highlightedText

        // Переход обратно на WelcomePage
        binding.buttonBackToWelcome.setOnClickListener {
            val intent = Intent(this, WelcomePageActivity::class.java)
            startActivity(intent)
        }
    }

}