package com.example.agarimoapp

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Profesional(val nombre: String? = null, val apellido: String? = null, val email: String? = null)
