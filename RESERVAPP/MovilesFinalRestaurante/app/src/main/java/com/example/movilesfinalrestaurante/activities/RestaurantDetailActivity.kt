package com.example.movilesfinalrestaurante.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movilesfinalrestaurante.activities.viewmodels.RestaurantDetailViewModel
import com.example.movilesfinalrestaurante.adapters.GalleryAdapter
import com.example.movilesfinalrestaurante.databinding.ActivityRestaurantDetailBinding
import com.example.movilesfinalrestaurante.mains.MenuActivity

class RestaurantDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantDetailBinding
    private var restaurantId: Int = 0
    val model: RestaurantDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtiene el id del restaurante
        restaurantId = intent.getIntExtra("RESTAURANT_ID", 0)
        if (restaurantId != 0) {
            model.getRestaurantDetails(restaurantId, this)
        } else {
            Toast.makeText(this, "Error: Restaurante no encontrado", Toast.LENGTH_SHORT).show()
            finish()
        }

        setupEventListeners()
        setupViewModelObservers()
    }

    private fun setupEventListeners() {
        binding.btnViewMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java) //lleva al menu
            intent.putExtra("RESTAURANT_ID", restaurantId) // Pasa el ID
            startActivity(intent)
        }

        binding.btnReserve.setOnClickListener {
            model.handleReservation(this, restaurantId) //reserva
        }
    }

    private fun setupViewModelObservers() {
        model.restaurant.observe(this) { restaurant ->
            restaurant?.let {
                binding.tvName.text = it.name
                binding.tvCity.text = it.city
                binding.tvDescription.text = it.description
                Glide.with(this).load(it.logo).into(binding.ivLogo)

                val photoList = it.photos
                if (photoList.isEmpty()) {
                    Toast.makeText(this, "No hay fotos disponibles para este restaurante", Toast.LENGTH_SHORT).show()
                } else {
                    // Configura el adaptador de la galería de fotos
                    val galleryAdapter = GalleryAdapter(photoList) { photoUrl ->
                        val intent = Intent(this, FotoActivity::class.java)
                        intent.putExtra("PHOTO_URL", photoUrl) // Pasa la URL de la foto
                        startActivity(intent)
                    }
                    binding.rvGallery.adapter = galleryAdapter
                    binding.rvGallery.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

                    Toast.makeText(this, "Cargando información del restaurante", Toast.LENGTH_SHORT).show()
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