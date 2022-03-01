package com.example.agarimoapp

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Paciente(val nombre: String? = null, val apellido: String? = null)
