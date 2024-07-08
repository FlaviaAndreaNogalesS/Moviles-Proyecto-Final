package com.example.movilesfinalrestaurante.activities.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import com.example.movilesfinalrestaurante.models.Restaurant
import com.example.movilesfinalrestaurante.repositories.RestaurantRepository
import com.example.movilesfinalrestaurante.repositories.PreferencesRepository
import com.example.movilesfinalrestaurante.activities.LoginActivity
import com.example.movilesfinalrestaurante.mains.ConfirmReservationActivity

class RestaurantDetailViewModel : ViewModel() {
    private val _restaurant: MutableLiveData<Restaurant?> by lazy {
        MutableLiveData<Restaurant?>()
    }
    val restaurant: LiveData<Restaurant?> get() = _restaurant

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getRestaurantDetails(id: Int, context: Context) {
        RestaurantRepository.getRestaurantDetails(id, success = { restaurant ->
            restaurant?.let {
                _restaurant.value = it
            } ?: run {
                _errorMessage.value = "Error: Restaurante no encontrado"
            }
        }, failure = { t ->
            _errorMessage.value = "Error: ${t.message}"
        })
    }

    fun handleReservation(context: Context, restaurantId: Int) {
        val token = PreferencesRepository.getToken(context)
        if (token.isNullOrEmpty()) {
            _errorMessage.value = "Por favor, inicie sesión para hacer una reserva" //pa hacer una reserva pide inicio de sesion
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        } else {
            val intent = Intent(context, ConfirmReservationActivity::class.java)
            intent.putExtra("RESTAURANT_ID", restaurantId) //si esta iniciado, le pasa el id pa hacer reserva
            context.startActivity(intent)
        }
    }
}