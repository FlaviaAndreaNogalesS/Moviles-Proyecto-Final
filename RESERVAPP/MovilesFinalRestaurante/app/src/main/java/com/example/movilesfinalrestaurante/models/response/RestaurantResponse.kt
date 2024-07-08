package com.example.movilesfinalrestaurante.models.response

import com.example.movilesfinalrestaurante.models.Restaurant

data class RestaurantResponse(
    val success: Boolean,
    val data: List<Restaurant>,
    val id: Int,
    val name: String,
    val city: String,
    val description: String,
    val logo: String,
    val user_id: Int,
    val created_at: String,
    val updated_at: String
)