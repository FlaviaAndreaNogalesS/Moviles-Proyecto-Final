package com.example.movilesfinalrestaurante.activities.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movilesfinalrestaurante.mains.MainActivity
import com.example.movilesfinalrestaurante.models.Restaurant
import com.example.movilesfinalrestaurante.models.RestaurantSearchFilters
import com.example.movilesfinalrestaurante.repositories.PreferencesRepository
import com.example.movilesfinalrestaurante.repositories.RestaurantRepository

class RestaurantListViewModel : ViewModel() {
    private val _restaurants: MutableLiveData<List<Restaurant>> by lazy {
        MutableLiveData<List<Restaurant>>()
    }
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    fun searchRestaurants(filters: RestaurantSearchFilters) { //filtro de busqueda
        RestaurantRepository.searchRestaurants(filters, success = { response ->
            response?.let {
                _restaurants.value = it
            }
        }, failure = { t ->
            _errorMessage.value = "Error: ${t.message}"
        })
    }

    fun logout(context: Context) { //cerrar sesion
        PreferencesRepository.clearToken(context)
        PreferencesRepository.saveUserName("", context)
        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        (context as Activity).finish()
    }
}
