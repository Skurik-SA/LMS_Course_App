package com.example.lms_course_app.utils

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat

class TextHighlighter(private val context: Context) {

    fun highlightText(text: String, phrases: List<String>, colorResId: Int): SpannableString {
        val spannableString = SpannableString(text)
        val color = ContextCompat.getColor(context, colorResId)

        phrases.forEach { phrase ->
            val start = text.indexOf(phrase)
            if (start >= 0) {
                val end = start + phrase.length
                spannableString.setSpan(
                    ForegroundColorSpan(color),
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        return spannableString
    }
}
