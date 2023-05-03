package com.suresh.dailywidget

import android.R.style.Widget
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var buttonAction: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val configIntent:Intent = intent
        buttonAction = findViewById(R.id.buttonAction)
        buttonAction.setOnClickListener {
            updateWidget()
        }
    }

    private fun updateWidget() {
        val updateWidget =
            Intent(this@MainActivity, Widget::class.java)
        updateWidget.action = "updateWidget"
        val pending = PendingIntent.getBroadcast(
            this@MainActivity,
            0,
            updateWidget,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        pending.send()
    }
}