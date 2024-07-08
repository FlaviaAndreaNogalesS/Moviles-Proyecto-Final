package com.example.movilesfinalrestaurante.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movilesfinalrestaurante.activities.viewmodels.RestaurantListViewModel
import com.example.movilesfinalrestaurante.adapters.RestaurantAdapter
import com.example.movilesfinalrestaurante.databinding.ActivityRestaurantListBinding
import com.example.movilesfinalrestaurante.models.Restaurant
import com.example.movilesfinalrestaurante.models.RestaurantSearchFilters
import com.example.movilesfinalrestaurante.repositories.PreferencesRepository

class RestaurantListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantListBinding
    private lateinit var adapter: RestaurantAdapter
    private val restaurants = mutableListOf<Restaurant>()
    val model: RestaurantListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUser()
        setupRecyclerView()
        setupEventListeners()
        setupViewModelObservers()

        // Inicialmente muestra todos los restaurantes
        model.searchRestaurants(RestaurantSearchFilters(null, null, null, null))
    }

    // Configura la información del usuario
    private fun setupUser() {
        val token = PreferencesRepository.getToken(this) //obtiene el token del usuario
        val userName = PreferencesRepository.getUserName(this)
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Usuario invitado, por favor inicie sesión", Toast.LENGTH_SHORT).show()
            binding.tvUserName.text = "Invitado" // si no incio sesion, es invitado
        } else {
            Toast.makeText(this, "Usuario autenticado", Toast.LENGTH_SHORT).show()
            binding.tvUserName.text = userName //trate de obtener el nombre del usuario pero xd
        }
    }

    // Configura el RecyclerView
    private fun setupRecyclerView() {
        adapter = RestaurantAdapter(restaurants) { restaurant ->
            val intent = Intent(this, RestaurantDetailActivity::class.java)
            intent.putExtra("RESTAURANT_ID", restaurant.id) // Pasa el ID del restaurante
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // Configura los listeners de los eventos
    private fun setupEventListeners() {
        binding.btnSearch.setOnClickListener {
            // Obtiene los valores de los filtros de búsqueda
            val city = binding.etCity.text.toString().trim()
            val date = binding.etDate.text.toString().trim()
            val startTime = binding.etStartTime.text.toString().trim()
            val endTime = binding.etEndTime.text.toString().trim()

            // Crea un objeto de filtros de búsqueda con los valores ingresados
            val filters = RestaurantSearchFilters(
                city = if (city.isNotEmpty()) city else null,
                selectedDate = if (date.isNotEmpty()) date else null,
                startTime = if (startTime.isNotEmpty()) startTime else null,
                endTime = if (endTime.isNotEmpty()) endTime else null
            )

            model.searchRestaurants(filters) // Llama al ViewModel para buscar
        }

        binding.btnMenu.setOnClickListener {
            val intent = Intent(this, OpcionesActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            model.logout(this)
        }
    }

    private fun setupViewModelObservers() {
        model.restaurants.observe(this) { restaurantList ->
            restaurants.clear()
            restaurants.addAll(restaurantList)
            adapter.notifyDataSetChanged()
        }

        model.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

