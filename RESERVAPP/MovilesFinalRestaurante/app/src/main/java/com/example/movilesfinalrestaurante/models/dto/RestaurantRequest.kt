package com.example.movilesfinalrestaurante.models.dto

data class RestaurantRequest(
    val name: String,
    val city: String,
    val description: String,
    val logo: String,
    val address: String
)
