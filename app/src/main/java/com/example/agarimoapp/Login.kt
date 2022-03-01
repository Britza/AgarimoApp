package com.example.agarimoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private val TAG = "RealTime"

    //Declaramos la variable auth
    private lateinit var auth: FirebaseAuth

    //Declaramos las variables de email, contraseña, registrar e iniciar
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var iniciar: Button
    lateinit var pacientesMap: Intent
    lateinit var profesionales: Intent
    lateinit var variable: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Inicializamos las variables creadas anteriormente
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        iniciar = findViewById(R.id.iniciar)

        //Traida la variable de "profesional" y "paciente"
        variable = intent.getStringExtra(EXTRA_MESSAGE).toString()

        //Intent profesionales que te envia a una empty activity
        profesionales = Intent(this, Profesionales::class.java)

        //Intent pacientes que te envia a un Mapfragment
        pacientesMap = Intent(this, Pacientes::class.java)

        //Inicializamos Fire base auth
        auth = Firebase.auth


        //Hacemos la llamada para el metodo de iniciar sesión
        iniciar.setOnClickListener {
            signIn(email.text.toString(), password.text.toString())

        }
    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d("estado", "" + auth.currentUser?.uid)
    }

    /**
     * Método que sirve para iniciar sesión a través de un email y contraseña
     */
    private fun signIn(email: String, password: String) {


        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Iniciamos sesion con la información del usuario.
                    Log.d(TAG, "signInWithEmail:success")
                    Log.d("estado", "inicio de sesión correcto")

                    val user = auth.currentUser
                    updateUI(user)

                    if ("paciente" == variable) {
                        startActivity(pacientesMap)
                    }
                    else{
                        startActivity(profesionales)
                    }


                } else {
                    //Si los datos no son correctos, envia un mensaje al usuario,
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Log.d("estado", "No se puedo iniciar sesion")

                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    fun registrate(View: View){
        var intent = Intent (this, Registrarse::class.java).apply {
            putExtra(EXTRA_MESSAGE, variable)
        }
        startActivity(intent)
    }
}