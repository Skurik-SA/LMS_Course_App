package com.example.lms_course_app

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lms_course_app.databinding.ActivityRegisterPageBinding

class RegisterPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Настройка ViewBinding
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val privacyPolicyText = findViewById<TextView>(R.id.privacyPolicyText)
        val text = getString(R.string.privacy_policy)

        val spannableString = SpannableString(text)

        val policyPhrase = getString(R.string.privacy_policy_key)
        val termsPhrase = getString(R.string.terms_of_service_key)

        val policyStart = text.indexOf(policyPhrase)
        val policyEnd = policyStart + policyPhrase.length

        val termsStart = text.indexOf(termsPhrase)
        val termsEnd = termsStart + termsPhrase.length

        val linkColor = ContextCompat.getColor(this, R.color.purple_700)

        if (policyStart >= 0) {
            spannableString.setSpan(
                ForegroundColorSpan(linkColor),
                policyStart,
                policyEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        if (termsStart >= 0) {
            spannableString.setSpan(
                ForegroundColorSpan(linkColor),
                termsStart,
                termsEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // Устанавливаем текст с выделением в TextView
        privacyPolicyText.text = spannableString

        // Переход обратно на WelcomePage
        binding.buttonBackToWelcome.setOnClickListener {
            val intent = Intent(this, WelcomePageActivity::class.java)
            startActivity(intent)
        }
    }

}