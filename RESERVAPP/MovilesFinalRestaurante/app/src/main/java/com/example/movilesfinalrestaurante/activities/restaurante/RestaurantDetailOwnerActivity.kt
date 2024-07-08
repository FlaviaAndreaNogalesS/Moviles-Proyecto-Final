package com.example.movilesfinalrestaurante.activities.restaurante

import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movilesfinalrestaurante.activities.viewmodels.RestaurantDetailOwnerViewModel
import com.example.movilesfinalrestaurante.databinding.ActivityRestaurantDetailOwnerBinding

class RestaurantDetailOwnerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantDetailOwnerBinding
    private var restaurantId: Int = 0
    val model: RestaurantDetailOwnerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailOwnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtiene el ID del restaurante desde los extras del intent
        restaurantId = intent.getIntExtra("RESTAURANT_ID", 0)
        if (restaurantId == 0) {
            Toast.makeText(this, "Error: Restaurante no encontrado", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            model.getOwnerRestaurantDetails(restaurantId, this)
        }

        setupEventListeners()
        setupViewModelObservers()
    }

    private fun setupEventListeners() {
        binding.btnViewReservations.setOnClickListener {
            val intent = Intent(this, RestaurantReservationsActivity::class.java) //reservas del restaurante del dueño
            intent.putExtra("RESTAURANT_ID", restaurantId)
            startActivity(intent)
        }
    }

    private fun setupViewModelObservers() {
        model.restaurant.observe(this) { restaurant ->
            restaurant?.let {
                // Actualiza la interfaz de usuario con los detalles del restaurante
                Glide.with(this).load(it.logo).into(binding.ivRestaurantLogo)
                binding.tvRestaurantName.text = it.name
                binding.tvCity.text = it.city
                binding.tvDescription.text = it.description
            }
        }

        model.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
