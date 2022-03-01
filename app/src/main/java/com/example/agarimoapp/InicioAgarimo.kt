package com.example.agarimoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

const val EXTRA_MESSAGE = "com.dam2a.intent.MESSAGE"

class InicioAgarimo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio_agarimo)
    }

    fun profesional(View: View) {

        val intent = Intent(this, Login::class.java).apply {
            putExtra(EXTRA_MESSAGE, "profesional")
        }
        startActivity(intent)

    }

    fun paciente(View: View) {
        val intent = Intent(this, Login::class.java).apply {
            putExtra(EXTRA_MESSAGE, "paciente")
        }
        startActivity(intent)
    }
}