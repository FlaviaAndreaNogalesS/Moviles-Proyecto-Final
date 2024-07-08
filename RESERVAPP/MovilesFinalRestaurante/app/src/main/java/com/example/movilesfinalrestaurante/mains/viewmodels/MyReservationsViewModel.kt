package com.example.movilesfinalrestaurante.mains.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movilesfinalrestaurante.activities.LoginActivity
import com.example.movilesfinalrestaurante.models.Reservation
import com.example.movilesfinalrestaurante.repositories.PreferencesRepository
import com.example.movilesfinalrestaurante.repositories.RestaurantRepository

class MyReservationsViewModel : ViewModel() {

    //mutablelivedata pa la lista de reservas del usuario
    private val _reservations: MutableLiveData<List<Reservation>> by lazy {
        MutableLiveData<List<Reservation>>()
    }
    val reservations: LiveData<List<Reservation>> get() = _reservations

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    //obtiene las reservas
    fun getReservations(context: Context) {
        val token = PreferencesRepository.getToken(context) //obtiene el token del usuario
        if (token.isNullOrEmpty()) {
            _errorMessage.value = "Por favor, inicie sesión para ver sus reservas"
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        } else {
            //llama al repositorio pa tener las reservas
            RestaurantRepository.getReservations(token, success = { reservationsList ->
                reservationsList?.let {
                    _reservations.value = it
                }
            }, failure = { t ->
                _errorMessage.value = "Error: ${t.message}"
            })
        }
    }
}
