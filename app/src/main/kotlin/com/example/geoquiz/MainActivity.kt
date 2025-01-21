package com.example.geoquiz

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener { view: View ->
            val toast = Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.START, 100, 100)
            toast.show()
        }
        falseButton.setOnClickListener { view: View ->
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        }
    }
}