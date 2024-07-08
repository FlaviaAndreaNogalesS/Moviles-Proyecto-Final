package com.example.movilesfinalrestaurante.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.movilesfinalrestaurante.R
import com.example.movilesfinalrestaurante.databinding.ActivityCreateRestaurantBinding
import com.example.movilesfinalrestaurante.models.dto.RestaurantRequest
import com.example.movilesfinalrestaurante.repositories.PreferencesRepository
import com.example.movilesfinalrestaurante.repositories.RestaurantRepository

class CreateRestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateRestaurantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateRestaurant.setOnClickListener {
            val name = binding.etRestaurantName.text.toString().trim()
            val city = binding.etCity.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            val logo = binding.etLogo.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()

            if (name.isNotEmpty() && city.isNotEmpty() && description.isNotEmpty() && logo.isNotEmpty() && address.isNotEmpty()) {
                createRestaurant(name, city, description, logo, address)
            } else {
                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createRestaurant(name: String, city: String, description: String, logo: String, address: String) {
        val token = PreferencesRepository.getToken(this)
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Por favor, inicie sesión para crear un restaurante", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val restaurantRequest = RestaurantRequest(name, city, description, logo, address)
            RestaurantRepository.createRestaurant(token, restaurantRequest, success = {
                Toast.makeText(this, "Restaurante creado exitosamente", Toast.LENGTH_SHORT).show()
                // Cargar la imagen del logo usando Glide
                Glide.with(this).load(logo).into(binding.ivRestaurantLogo)
                finish()
            }, failure = { t ->
                Log.e("CreateRestaurantActivity", "Error al crear restaurante", t)
                Toast.makeText(this, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            })
        }
    }
}