package com.example.movilesfinalrestaurante.mains

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movilesfinalrestaurante.adapters.ReservationsAdapter
import com.example.movilesfinalrestaurante.databinding.ActivityMyReservationsBinding
import com.example.movilesfinalrestaurante.mains.viewmodels.MyReservationsViewModel
import com.example.movilesfinalrestaurante.models.Reservation

class MyReservationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyReservationsBinding
    private lateinit var adapter: ReservationsAdapter
    private val reservations = mutableListOf<Reservation>()
    val model: MyReservationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReservationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModelObservers()

        model.getReservations(this) // Solicita las reservas del usuario al ViewModel
    }

    override fun onResume() {
        super.onResume()
        model.getReservations(this) // vuelve a cargar la lista cuando regresamos a la lista
    }

    private fun setupRecyclerView() {
        adapter = ReservationsAdapter(reservations) { reservation ->
            //al dar clic a una reserva
            val intent = Intent(this, ReservationDetailActivity::class.java)
            intent.putExtra("RESERVATION_ID", reservation.id)  // Pasa el ID de la reserva
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModelObservers() {
        model.reservations.observe(this) { reservationsList ->
            reservationsList?.let {
                reservations.clear()
                reservations.addAll(it)
                adapter.notifyDataSetChanged()
                if (reservations.isEmpty()) {
                    Toast.makeText(this, "No hay reservas", Toast.LENGTH_SHORT).show()
                }
            }
        }

        model.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
