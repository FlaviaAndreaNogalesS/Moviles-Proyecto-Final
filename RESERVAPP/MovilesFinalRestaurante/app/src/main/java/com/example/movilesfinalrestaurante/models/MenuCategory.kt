package com.example.movilesfinalrestaurante.models

data class MenuCategory(
    val id: Int,
    val name: String,
    val restaurant_id: Long,
    val plates: List<Plate>
)
