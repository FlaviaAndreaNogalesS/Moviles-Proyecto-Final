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

class ReservationDetailViewModel : ViewModel() {

    // LiveData para almacenar los detalles de la reserva
    private val _reservation: MutableLiveData<Reservation?> by lazy {
        MutableLiveData<Reservation?>()
    }
    val reservation: LiveData<Reservation?> get() = _reservation

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _successMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val successMessage: LiveData<String> get() = _successMessage

    // obtiene los detalles de una reserva específica
    fun getReservationDetails(id: Int, context: Context) {
        val token = PreferencesRepository.getToken(context)
        if (token.isNullOrEmpty()) {
            _errorMessage.value = "Por favor, inicie sesión para ver el detalle de la reserva"
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        } else {
            // Llama al repositorio para obtener los detalles de la reserva
            RestaurantRepository.getReservationDetails(token, id, success = { reservation ->
                reservation?.let {
                    _reservation.value = it // Actualiza el LiveData con los detalles de la reserva
                }
            }, failure = { t ->
                _errorMessage.value = "Error: ${t.message}"
            })
        }
    }

    //cancela una reserva específica
    fun cancelReservation(id: Int, context: Context) {
        val token = PreferencesRepository.getToken(context)
        if (token.isNullOrEmpty()) {
            _errorMessage.value = "Por favor, inicie sesión para cancelar la reserva"
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        } else {
            // Llama al repositorio para cancelar la reserva
            RestaurantRepository.cancelReservation(token, id, success = {
                _successMessage.value = "Reserva cancelada exitosamente"  // Actualiza el mensaje de éxito
            }, failure = { t ->
                _errorMessage.value = "Error: ${t.message}"
            })
        }
    }
}
