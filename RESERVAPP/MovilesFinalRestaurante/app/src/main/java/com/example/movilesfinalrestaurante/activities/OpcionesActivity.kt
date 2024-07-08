package com.example.movilesfinalrestaurante.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movilesfinalrestaurante.databinding.ActivityOpcionesBinding
import com.example.movilesfinalrestaurante.mains.MyReservationsActivity
import com.example.movilesfinalrestaurante.mains.restaurante.MyRestaurantsActivity

class OpcionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpcionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMyReservations.setOnClickListener {
            val intent = Intent(this, MyReservationsActivity::class.java)
            startActivity(intent)
        }

        binding.btnCreateRestaurant.setOnClickListener {
            val intent = Intent(this, CreateRestaurantActivity::class.java)
            startActivity(intent)
        }

        binding.btnMyRestaurants.setOnClickListener {
            val intent = Intent(this, MyRestaurantsActivity::class.java)
            startActivity(intent)
        }
    }
}
