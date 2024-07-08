package com.example.movilesfinalrestaurante.repositories

import com.example.movilesfinalrestaurante.API.ApiService
import com.example.movilesfinalrestaurante.models.response.ApiResponse
import com.example.movilesfinalrestaurante.models.dto.LoginRequestDTO
import com.example.movilesfinalrestaurante.models.response.LoginResponseDTO
import com.example.movilesfinalrestaurante.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

    fun doLogin(email: String, password: String, success: (LoginResponseDTO?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.loginUser(LoginRequestDTO(email, password)).enqueue(object : Callback<LoginResponseDTO> {
            override fun onResponse(call: Call<LoginResponseDTO>, response: Response<LoginResponseDTO>) {
                if (response.code() == 401) {
                    success(null)
                } else {
                    success(response.body())
                }
            }

            override fun onFailure(call: Call<LoginResponseDTO>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun registerUser(user: User, success: (ApiResponse?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiService::class.java)

        service.registerUser(user).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    if (response.code() == 400) {
                        failure(Exception("400 - El usuario ya existe"))
                    } else {
                        failure(Exception("Error en la respuesta de la API: ${response.code()} - $errorResponse"))
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                failure(t)
            }
        })
    }
}
