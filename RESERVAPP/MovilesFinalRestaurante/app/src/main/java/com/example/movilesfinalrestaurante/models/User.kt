package com.example.movilesfinalrestaurante.models

data class User(
    val email: String,
    val password: String,
    val name: String? = null,
    val phone: String? = null
)