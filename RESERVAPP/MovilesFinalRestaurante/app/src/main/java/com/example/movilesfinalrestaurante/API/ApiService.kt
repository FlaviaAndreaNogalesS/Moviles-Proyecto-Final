package com.example.movilesfinalrestaurante.API

import com.example.movilesfinalrestaurante.models.MenuCategory
import com.example.movilesfinalrestaurante.models.Reservation
import com.example.movilesfinalrestaurante.models.response.ApiResponse
import com.example.movilesfinalrestaurante.models.dto.LoginRequestDTO
import com.example.movilesfinalrestaurante.models.response.LoginResponseDTO
import com.example.movilesfinalrestaurante.models.Restaurant
import com.example.movilesfinalrestaurante.models.RestaurantSearchFilters
import com.example.movilesfinalrestaurante.models.User
import com.example.movilesfinalrestaurante.models.dto.ReservationRequest
import com.example.movilesfinalrestaurante.models.dto.RestaurantRequest
import com.example.movilesfinalrestaurante.models.response.ReservationResponse
import com.example.movilesfinalrestaurante.models.response.RestaurantResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("loginuser")
    fun loginUser(@Body user: LoginRequestDTO): Call<LoginResponseDTO>

    @POST("registeruser")
    fun registerUser(@Body user: User): Call<ApiResponse>

    @POST("restaurants/search")
    fun searchRestaurants(@Body filters: RestaurantSearchFilters): Call<List<Restaurant>>

    @GET("restaurants/{id}")
    fun getRestaurantDetails(@Path("id") id: Int): Call<Restaurant>

    @GET("restaurants/{id}/menu")
    fun getMenu(@Path("id") id: Int): Call<List<MenuCategory>>

    @POST("reservations")
    fun makeReservation(@Header("Authorization") token: String, @Body request: ReservationRequest): Call<ReservationResponse>

    @GET("reservations")
    fun getReservations(@Header("Authorization") token: String): Call<List<Reservation>>

    @GET("reservations/{id}")
    fun getReservationDetails(@Header("Authorization") token: String, @Path("id") id: Int): Call<Reservation>

    @POST("reservations/{id}/cancel")
    fun cancelReservation(@Header("Authorization") token: String, @Path("id") id: Int): Call<Void>

    @POST("reservations/{id}/confirm")
    fun confirmReservation(@Header("Authorization") token: String, @Path("id") id: Int): Call<Void>

    @GET("restaurants")
    fun getUserRestaurants(@Header("Authorization") token: String): Call<List<Restaurant>>

    @GET("restaurants/{id}")
    fun getOwnerRestaurantDetails(@Header("Authorization") token: String, @Path("id") id: Int): Call<Restaurant>

    @GET("restaurants/{id}/reservations")
    fun getRestaurantReservations(@Header("Authorization") token: String, @Path("id") id: Int): Call<List<Reservation>>

    @POST("restaurants")
    fun createRestaurant(@Header("Authorization") token: String, @Body restaurant: RestaurantRequest): Call<RestaurantResponse>
}
