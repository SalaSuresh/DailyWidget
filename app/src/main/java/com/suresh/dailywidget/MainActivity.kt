package com.suresh.dailywidget

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttonAction: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonAction = findViewById(R.id.buttonAction)
        buttonAction.setOnClickListener {
            updateWidget()
        }
    }

    private fun updateWidget() {
        Toast.makeText(this, "Update Widget", Toast.LENGTH_SHORT).show()
    }
}