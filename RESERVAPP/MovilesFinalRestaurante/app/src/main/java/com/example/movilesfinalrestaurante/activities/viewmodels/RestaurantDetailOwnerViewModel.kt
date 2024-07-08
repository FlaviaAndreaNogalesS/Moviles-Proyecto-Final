package com.example.movilesfinalrestaurante.activities.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movilesfinalrestaurante.activities.LoginActivity
import com.example.movilesfinalrestaurante.models.Restaurant
import com.example.movilesfinalrestaurante.repositories.PreferencesRepository
import com.example.movilesfinalrestaurante.repositories.RestaurantRepository

class RestaurantDetailOwnerViewModel : ViewModel() {

    // LiveData pa almacenar los detalles
    private val _restaurant: MutableLiveData<Restaurant?> by lazy {
        MutableLiveData<Restaurant?>()
    }
    val restaurant: LiveData<Restaurant?> get() = _restaurant

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    // obtiene los detalles del restaurante del propietario
    fun getOwnerRestaurantDetails(id: Int, context: Context) {
        // Obtiene el token de autenticación
        val token = PreferencesRepository.getToken(context)
        if (token.isNullOrEmpty()) {
            _errorMessage.value = "Por favor, inicie sesión para ver los detalles del restaurante"
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        } else {
            // Llama al repositorio para obtener los detalles
            RestaurantRepository.getOwnerRestaurantDetails(token, id, success = { restaurant ->
                restaurant?.let {
                    _restaurant.value = it
                }
            }, failure = { t ->
                _errorMessage.value = "Error: ${t.message}"
            })
        }
    }
}

