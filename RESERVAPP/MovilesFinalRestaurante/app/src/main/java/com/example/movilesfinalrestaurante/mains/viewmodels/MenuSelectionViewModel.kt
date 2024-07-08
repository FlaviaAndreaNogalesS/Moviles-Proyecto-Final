package com.example.movilesfinalrestaurante.mains.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movilesfinalrestaurante.activities.LoginActivity
import com.example.movilesfinalrestaurante.models.Food
import com.example.movilesfinalrestaurante.models.MenuCategory
import com.example.movilesfinalrestaurante.models.Plate
import com.example.movilesfinalrestaurante.models.dto.ReservationRequest
import com.example.movilesfinalrestaurante.repositories.PreferencesRepository
import com.example.movilesfinalrestaurante.repositories.RestaurantRepository

class MenuSelectionViewModel : ViewModel() {

    // LiveData para almacenar las categorías del menú
    private val _menuCategories: MutableLiveData<List<MenuCategory>> by lazy {
        MutableLiveData<List<MenuCategory>>()
    }
    val menuCategories: LiveData<List<MenuCategory>> get() = _menuCategories

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _successMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val successMessage: LiveData<String> get() = _successMessage

    // Listas para almacenar los platos seleccionados y sus cantidades
    private val selectedPlates = mutableListOf<Plate>()
    private val selectedPlateQuantities = mutableMapOf<Int, Int>()

    // Obtiene el menú de un restaurante
    fun getMenu(restaurantId: Int) {
        RestaurantRepository.getMenu(restaurantId, success = { menuCategories ->
            menuCategories?.let {
                _menuCategories.value = it
            }
        }, failure = { t ->
            _errorMessage.value = "Error: ${t.message}"
        })
    }

    //Maneja la selección de un plato
    fun onPlateSelected(plate: Plate, isSelected: Boolean) {
        if (isSelected) {
            selectedPlates.add(plate) // Añade el plato a la lista de seleccionados
            selectedPlateQuantities[plate.id] = 1 // Establece la cantidad predeterminada del plato a 1
        } else {
            selectedPlates.remove(plate) //elimina el plato de la lista de seleccionado
            selectedPlateQuantities.remove(plate.id)
        }
    }

    //confirma una reserva
    fun confirmReservation(restaurantId: Int, reservationDate: String, reservationTime: String, context: Context) {
        val token = PreferencesRepository.getToken(context) // Obtiene el token del usuario
        if (token.isNullOrEmpty()) {
            _errorMessage.value = "Por favor, inicie sesión para hacer una reserva"
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        } else {
            // Crea la lista de alimentos seleccionados
            val foodList = if (selectedPlates.isNotEmpty()) {
                selectedPlates.map { Food(it.id, selectedPlateQuantities[it.id] ?: 1) }
            } else {
                null
            }

            // Crea una solicitud de reserva con los datos proporcionados
            val reservationRequest = ReservationRequest(
                restaurant_id = restaurantId,
                date = reservationDate,
                time = reservationTime,
                people = 2,
                food = foodList
            )

            // Llama al repositorio para hacer la reserva
            RestaurantRepository.makeReservation(token, reservationRequest, success = { response ->
                response?.let {
                    _successMessage.value = "Reserva exitosa"
                }
            }, failure = { t ->
                _errorMessage.value = "Error: ${t.message}"
            })
        }
    }
}

