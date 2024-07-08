package com.example.movilesfinalrestaurante.models

data class Plate(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val menu_category_id: Int
)
