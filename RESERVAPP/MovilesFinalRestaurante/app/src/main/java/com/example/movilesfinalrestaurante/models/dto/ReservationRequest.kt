package com.example.movilesfinalrestaurante.models.dto

import com.example.movilesfinalrestaurante.models.Food

data class ReservationRequest(
    val restaurant_id: Int,
    val date: String,
    val time: String,
    val people: Int,
    val food: List<Food>? = null
)