package com.example.agarimoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Registrarse : AppCompatActivity() {

    private val TAG = "RealTime"

    //Declaramos la variable auth
    private lateinit var auth: FirebaseAuth

    //Declaramos la variable de database
    private lateinit var database: DatabaseReference

    //Declaramos las variables
    lateinit var nombre: EditText
    lateinit var apellido: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var registrar: Button
    lateinit var pacientesMap: Intent
    lateinit var profesionales: Intent
    lateinit var variable: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)


        //Inicializamos las variables creadas anteriormente
        nombre = findViewById(R.id.nombre)
        apellido = findViewById(R.id.apellido)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        registrar = findViewById(R.id.registrar)

        //Intent pacientes que te envia a un Mapfragment
        pacientesMap = Intent(this, Pacientes::class.java)

        //Intent profesionales que te envia a una empty activity
        profesionales = Intent(this, Profesionales::class.java)

        //Traida la variable de "profesional" y "paciente"
        variable = intent.getStringExtra(EXTRA_MESSAGE).toString()

        //Inicializamos Fire base auth
        auth = Firebase.auth

        //Inicializamos la database
        database = Firebase.database("https://trimestre-218c9-default-rtdb.europe-west1.firebasedatabase.app/").reference

        //Hacemos la llamada para el metodo de crear cuenta
        registrar.setOnClickListener {
            createAccount(email.text.toString(), password.text.toString())


        }
    }

    /**
     * Metodo que sirve para subir los datos a la base de datos de realtime
     */
    private fun realtimeBasePacProf(uidp: FirebaseUser?){
        Log.d(TAG, "Escribiendo datos")
        Log.d("estado", "escribe los datos")

        if ("paciente" == variable) {

            val nombrep = nombre.text.toString()
            val apellidop = apellido.text.toString()
            val emailp = email.text.toString()
            val pac = Paciente(nombrep, apellidop, emailp)
            Log.d(TAG, pac.toString())

            database.child("Pacientes/" + uidp!!.uid).setValue(pac)
        }
        else{

            val nombrepro = nombre.text.toString()
            val apellidopro = apellido.text.toString()
            val emailpro = email.text.toString()
            val prof = Profesional(nombrepro, apellidopro, emailpro)
            Log.d(TAG, prof.toString())

            database.child("Profesionales/" + uidp!!.uid).setValue(prof)

        }

    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d("estado", "" + auth.currentUser?.uid)
    }

    /**
     * Método que sirve para crear una cuenta a través de un email y contraseña
     */

    private fun createAccount(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Empezamos creando un usuario con email.
                    Log.d(TAG, "createUserWithEmail:success")
                    Log.d("estado", "usuario registrado")
                    val user = auth.currentUser
                    updateUI(user)
                    realtimeBasePacProf(user)

                    if ("paciente" == variable){
                        startActivity(pacientesMap)
                    }
                    else{
                        startActivity(profesionales)
                    }

                } else {
                    // Si el inicio de sesión falla, enviamos un mensaje al usuario.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Log.d("estado", "usuario NO registrado")

                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }

    }

}