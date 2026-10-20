package com.example.lab3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab3.ui.theme.Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_main)

        var switch1 = findViewById<Button>(R.id.switch1)
        var switch2 = findViewById<Button>(R.id.switch2)

        switch1.setOnClickListener {
            val intent = Intent(this, DataBaseV1::class.java)
            startActivity(intent)
        }

        switch2.setOnClickListener {
            val intent = Intent(this, DataBaseV2::class.java)
            startActivity(intent)
        }
    }
}
