package com.example.movilesfinalrestaurante.mains

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movilesfinalrestaurante.activities.RestaurantListActivity
import com.example.movilesfinalrestaurante.adapters.MenuAdapter
import com.example.movilesfinalrestaurante.databinding.ActivityMenuSelectionBinding
import com.example.movilesfinalrestaurante.mains.viewmodels.MenuSelectionViewModel
import com.example.movilesfinalrestaurante.models.Plate

class MenuSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuSelectionBinding
    private var restaurantId: Int = 0
    private var reservationDate: String? = null
    private var reservationTime: String? = null
    val model: MenuSelectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtiene los datos de la reserva desde los extras del intent
        restaurantId = intent.getIntExtra("RESTAURANT_ID", 0)
        reservationDate = intent.getStringExtra("RESERVATION_DATE")
        reservationTime = intent.getStringExtra("RESERVATION_TIME")
        if (restaurantId == 0 || reservationDate == null || reservationTime == null) {
            Toast.makeText(this, "Error: Datos de reserva incompletos", Toast.LENGTH_SHORT).show()
            finish()
        }

        model.getMenu(restaurantId) // Solicita el menú del restaurante al ViewModel

        setupEventListeners()
        setupViewModelObservers()
    }

    private fun setupEventListeners() {
        binding.btnConfirmSelection.setOnClickListener {
            // Llama al ViewModel para confirmar la reserva
            model.confirmReservation(restaurantId, reservationDate!!, reservationTime!!, this)
        }
    }

    private fun setupViewModelObservers() {
        model.menuCategories.observe(this) { menuCategories ->
            menuCategories?.let {
                // Configura el adaptador del RecyclerView con las categorías del menú
                val menuAdapter = MenuAdapter(it, ::onPlateSelected)
                binding.recyclerView.adapter = menuAdapter
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
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
                if (it == "Reserva exitosa") {
                    val intent = Intent(this, RestaurantListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    //maneja la selección de un plato
    private fun onPlateSelected(plate: Plate, isSelected: Boolean) {
        model.onPlateSelected(plate, isSelected)
    }
}

