package com.example.movilesfinalrestaurante.activities.viewmodels

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import com.example.movilesfinalrestaurante.activities.LoginActivity
import com.example.movilesfinalrestaurante.models.Reservation
import com.example.movilesfinalrestaurante.repositories.PreferencesRepository
import com.example.movilesfinalrestaurante.repositories.RestaurantRepository

class RestaurantReservationsViewModel : ViewModel() {
    private val _reservations: MutableLiveData<List<Reservation>> by lazy {
        MutableLiveData<List<Reservation>>()
    }
    val reservations: LiveData<List<Reservation>> get() = _reservations

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    // obtiene las reservas del restaurante
    fun getRestaurantReservations(id: Int, context: Context) { //token
        val token = PreferencesRepository.getToken(context)
        if (token.isNullOrEmpty()) {
            _errorMessage.value = "Por favor, inicie sesión para ver las reservas del restaurante"
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        } else {
            // Llama al repositorio para obtener las reservas del restaurante
            RestaurantRepository.getRestaurantReservations(token, id, success = { reservationsList ->
                reservationsList?.let {
                    // Filtra las reservas que no están confirmadas
                    _reservations.value = it.filter { reservation -> reservation.status != "confirmed" } //era pa que se quiten las confirmadas anteriores
                    Log.d("RestaurantReservations", "Reservas recibidas: ${it.size}") //esto era pa ver si funcionaba xd
                }
            }, failure = { t ->
                Log.e("RestaurantReservations", "Error al obtener reservas: ${t.message}")
                _errorMessage.value = "Error: ${t.message}"
            })
        }
    }

    // cancela una reserva
    fun cancelReservation(reservation: Reservation, context: Context) {
        val token = PreferencesRepository.getToken(context) //token
        if (token.isNullOrEmpty()) {
            _errorMessage.value = "Por favor, inicie sesión para cancelar la reserva"
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        } else {
            RestaurantRepository.cancelReservation(token, reservation.id, success = {
                _errorMessage.value = "Reserva cancelada exitosamente"
                getRestaurantReservations(reservation.restaurant.id, context)  // Actualiza la lista de reservas después de cancelar una
            }, failure = { t ->
                _errorMessage.value = "Error: ${t.message}"
            })
        }
    }

    // confirma una reserva
    fun confirmReservation(reservation: Reservation, context: Context) {
        val token = PreferencesRepository.getToken(context)
        if (token.isNullOrEmpty()) {
            _errorMessage.value = "Por favor, inicie sesión para confirmar la reserva"
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        } else {
            // Llama al repositorio para confirmar la reserva
            RestaurantRepository.confirmReservation(token, reservation.id, success = {
                _errorMessage.value = "Reserva confirmada exitosamente" // Actualiza el mensaje de éxito
                val currentReservations = _reservations.value?.toMutableList() ?: mutableListOf()
                currentReservations.remove(reservation) // Elimina la reserva confirmada de la lista
                _reservations.value = currentReservations // Actualiza el LiveData con la lista actualizad
            }, failure = { t ->
                _errorMessage.value = "Error: ${t.message}"
            })
        }
    }
}
