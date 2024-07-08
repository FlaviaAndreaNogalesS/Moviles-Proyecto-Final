package com.example.movilesfinalrestaurante.activities.restaurante

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movilesfinalrestaurante.activities.viewmodels.RestaurantReservationsViewModel
import com.example.movilesfinalrestaurante.adapters.ReservasRestauranteAdapter
import com.example.movilesfinalrestaurante.databinding.ActivityRestaurantReservationsBinding
import com.example.movilesfinalrestaurante.models.Reservation

class RestaurantReservationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantReservationsBinding
    private lateinit var adapter: ReservasRestauranteAdapter
    private val reservations = mutableListOf<Reservation>()
    private var restaurantId: Int = 0
    val model: RestaurantReservationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantReservationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtiene el id
        restaurantId = intent.getIntExtra("RESTAURANT_ID", 0)
        if (restaurantId == 0) {
            Toast.makeText(this, "Error: Restaurante no encontrado", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            model.getRestaurantReservations(restaurantId, this)
        }

        setupRecyclerView()
        setupViewModelObservers()
    }

    private fun setupRecyclerView() {
        // Configura el adaptador del RecyclerView con las reservas
        adapter = ReservasRestauranteAdapter(reservations, { reservation ->
            model.cancelReservation(reservation, this) //cancela
        }, { reservation ->
            model.confirmReservation(reservation, this) //confirma
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModelObservers() {
        model.reservations.observe(this) { reservationsList ->
            reservations.clear() // Limpia la lista
            reservations.addAll(reservationsList) // Añade las nuevas
            adapter.notifyDataSetChanged()
            if (reservations.isEmpty()) {
                Toast.makeText(this, "No hay reservas", Toast.LENGTH_SHORT).show()
            }
        }

        model.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
