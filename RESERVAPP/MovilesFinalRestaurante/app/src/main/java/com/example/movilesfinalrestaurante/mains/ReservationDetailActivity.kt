package com.example.movilesfinalrestaurante.mains

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.movilesfinalrestaurante.databinding.ActivityReservationDetailBinding
import com.example.movilesfinalrestaurante.mains.viewmodels.ReservationDetailViewModel

class ReservationDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationDetailBinding
    private var reservationId: Int = 0
    val model: ReservationDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtiene el id de la reserva del usuario
        reservationId = intent.getIntExtra("RESERVATION_ID", 0)
        if (reservationId == 0) {
            Toast.makeText(this, "Error: Reserva no encontrada", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            model.getReservationDetails(reservationId, this)
        }

        setupEventListeners()
        setupViewModelObservers()
    }

    private fun setupEventListeners() {
        binding.btnCancelReservation.setOnClickListener {
            model.cancelReservation(reservationId, this)
        }
    }

    private fun setupViewModelObservers() {
        model.reservation.observe(this) { reservation ->
            reservation?.let {
                Glide.with(this).load(it.restaurant.logo).into(binding.ivRestaurantLogo)
                binding.tvRestaurantName.text = it.restaurant.name
                binding.tvCity.text = it.restaurant.city
                binding.tvReservationDate.text = it.date
                binding.tvReservationTime.text = it.time
                binding.tvPeople.text = it.people.toString()
                binding.tvStatus.text = it.status
            }
        }

        model.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        model.successMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                if (it == "Reserva cancelada exitosamente") {
                    finish()  // Cierra la actividad y vuelve a la lista de reservas
                }
            }
        }
    }
}

