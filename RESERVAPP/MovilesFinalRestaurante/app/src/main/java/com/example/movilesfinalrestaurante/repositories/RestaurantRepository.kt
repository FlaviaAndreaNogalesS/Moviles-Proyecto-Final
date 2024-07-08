package com.example.movilesfinalrestaurante.repositories

import android.util.Log
import com.example.movilesfinalrestaurante.API.ApiService
import com.example.movilesfinalrestaurante.models.MenuCategory
import com.example.movilesfinalrestaurante.models.Reservation
import com.example.movilesfinalrestaurante.models.Restaurant
import com.example.movilesfinalrestaurante.models.RestaurantSearchFilters
import com.example.movilesfinalrestaurante.models.dto.ReservationRequest
import com.example.movilesfinalrestaurante.models.dto.RestaurantRequest
import com.example.movilesfinalrestaurante.models.response.ReservationResponse
import com.example.movilesfinalrestaurante.models.response.RestaurantResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestaurantRepository {

    // Busca los restaurantes según los filtros proporcionados
    fun searchRestaurants(filters: RestaurantSearchFilters, success: (List<Restaurant>?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.searchRestaurants(filters).enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                failure(t)
            }
        })
    }

    // Obtiene los detalles de un restaurante
    fun getRestaurantDetails(id: Int, success: (Restaurant?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.getRestaurantDetails(id).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    // Obtiene el menú de un restaurante
    fun getMenu(restaurantId: Int, success: (List<MenuCategory>?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.getMenu(restaurantId).enqueue(object : Callback<List<MenuCategory>> {
            override fun onResponse(call: Call<List<MenuCategory>>, response: Response<List<MenuCategory>>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                }
            }

            override fun onFailure(call: Call<List<MenuCategory>>, t: Throwable) {
                failure(t)
            }
        })
    }

    // Obtiene las reservas del usuario autenticado
    fun getReservations(token: String, success: (List<Reservation>?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.getReservations("Bearer $token").enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                }
            }

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                failure(t)
            }
        })
    }

    // Obtiene los detalles de una reserva especifica
    fun getReservationDetails(token: String, id: Int, success: (Reservation?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.getReservationDetails("Bearer $token", id).enqueue(object : Callback<Reservation> {
            override fun onResponse(call: Call<Reservation>, response: Response<Reservation>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                }
            }

            override fun onFailure(call: Call<Reservation>, t: Throwable) {
                failure(t)
            }
        })
    }

    // Realiza una reserva en un restaurante
    fun makeReservation(
        token: String,
        reservationRequest: ReservationRequest,
        success: (ReservationResponse?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.makeReservation("Bearer $token", reservationRequest).enqueue(object : Callback<ReservationResponse> {
            override fun onResponse(call: Call<ReservationResponse>, response: Response<ReservationResponse>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                }
            }

            override fun onFailure(call: Call<ReservationResponse>, t: Throwable) {
                failure(t)
            }
        })
    }

    // Cancela una reserva
    fun cancelReservation(token: String, id: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.cancelReservation("Bearer $token", id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    val errorResponse = response.errorBody()?.string()
                    failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }

    // Obtiene los restaurantes del usuario
    fun getUserRestaurants(token: String, success: (List<Restaurant>?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.getUserRestaurants("Bearer $token").enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                failure(t)
            }
        })
    }

    // Obtiene los detalles del restaurantes que es dueño
    fun getOwnerRestaurantDetails(token: String, id: Int, success: (Restaurant?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.getOwnerRestaurantDetails("Bearer $token", id).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    // Obtiene las reservas de un restaurante específico
    fun getRestaurantReservations(token: String, id: Int, success: (List<Reservation>?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.getRestaurantReservations("Bearer $token", id).enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                }
            }

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                failure(t)
            }
        })
    }

    // Confirma una reserva
    fun confirmReservation(token: String, id: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.confirmReservation("Bearer $token", id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    val errorResponse = response.errorBody()?.string()
                    failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }

    //crea restaurante
    fun createRestaurant(token: String, restaurantRequest: RestaurantRequest, success: (RestaurantResponse?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.createRestaurant("Bearer $token", restaurantRequest).enqueue(object : Callback<RestaurantResponse> {
            override fun onResponse(call: Call<RestaurantResponse>, response: Response<RestaurantResponse>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    val exception = Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse")
                    Log.e("RestaurantRepository", exception.message.toString())
                    failure(exception)
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                Log.e("RestaurantRepository", "Error en la solicitud de creación de restaurante", t)
                failure(t)
            }
        })
    }
}
