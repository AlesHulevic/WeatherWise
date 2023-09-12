package com.example.tolaaleksey.weatherinfo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text : TextView = findViewById(R.id.main_text)
        val button : Button = findViewById(R.id.main_button)
        button.setOnClickListener(){
            text.text = "Ты пидор"
        }
    }

}

