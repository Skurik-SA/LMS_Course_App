package com.example.lms_course_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class HelloUserActivity : AppCompatActivity(R.layout.activity_hello_user) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val editText = findViewById<EditText>(R.id.inputUserName)
        val button = findViewById<Button>(R.id.buttonUserName)

        button.setOnClickListener {
            val intent = Intent(this, HelloWorldActivity::class.java)
            startActivity(intent)
        }
    }

}