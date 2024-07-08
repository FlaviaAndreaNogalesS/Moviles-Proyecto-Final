package com.example.movilesfinalrestaurante.mains

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movilesfinalrestaurante.R
import com.example.movilesfinalrestaurante.activities.LoginActivity
import com.example.movilesfinalrestaurante.activities.RegisterActivity
import com.example.movilesfinalrestaurante.activities.RestaurantListActivity
import com.example.movilesfinalrestaurante.databinding.ActivityMainBinding
import com.example.movilesfinalrestaurante.repositories.PreferencesRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logo.setImageResource(R.drawable.logo)

        // Verifica si el token está presente
        val token = PreferencesRepository.getToken(this)
        if (!token.isNullOrEmpty()) {
            // Si el token está presente
            val intent = Intent(this, RestaurantListActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnViewRestaurants.setOnClickListener {
            val intent = Intent(this, RestaurantListActivity::class.java)
            startActivity(intent)
        }

    }
}