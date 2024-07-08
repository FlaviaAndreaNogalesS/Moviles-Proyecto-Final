package com.example.movilesfinalrestaurante.models

data class Restaurant(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val description: String,
    val userID: Long,
    val logo: String,
    val owner: Owner,
    val photos: List<Photo> = emptyList()
)