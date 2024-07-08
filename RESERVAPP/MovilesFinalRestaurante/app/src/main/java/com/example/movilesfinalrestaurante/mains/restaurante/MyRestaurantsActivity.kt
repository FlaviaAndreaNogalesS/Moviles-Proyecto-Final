package com.example.movilesfinalrestaurante.mains.restaurante

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import android.content.Intent
import com.example.movilesfinalrestaurante.activities.LoginActivity
import com.example.movilesfinalrestaurante.activities.restaurante.RestaurantDetailOwnerActivity
import com.example.movilesfinalrestaurante.adapters.RestaurantAdapter
import com.example.movilesfinalrestaurante.databinding.ActivityMyRestaurantsBinding
import com.example.movilesfinalrestaurante.models.Restaurant
import com.example.movilesfinalrestaurante.repositories.RestaurantRepository
import com.example.movilesfinalrestaurante.repositories.PreferencesRepository

class MyRestaurantsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyRestaurantsBinding
    private lateinit var adapter: RestaurantAdapter
    private val restaurants = mutableListOf<Restaurant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyRestaurantsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RestaurantAdapter(restaurants) { restaurant ->
            val intent = Intent(this, RestaurantDetailOwnerActivity::class.java)
            intent.putExtra("RESTAURANT_ID", restaurant.id) // Pasa el ID del restaurante
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        getUserRestaurants()
    }

    // Obtiene los restaurantes del usuario
    private fun getUserRestaurants() {
        val token = PreferencesRepository.getToken(this)
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Por favor, inicie sesión para ver sus restaurantes", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            // Llama al repositorio para obtener los restaurantes del usuario
            RestaurantRepository.getUserRestaurants(token, success = { restaurantsList ->
                restaurantsList?.let {
                    restaurants.clear()
                    restaurants.addAll(it)
                    adapter.notifyDataSetChanged()
                    if (restaurants.isEmpty()) {
                        Toast.makeText(this, "No hay restaurantes", Toast.LENGTH_SHORT).show()
                    }
                }
            }, failure = { t ->
                Toast.makeText(this, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
