package com.example.movilesfinalrestaurante.mains

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movilesfinalrestaurante.adapters.MenuAdapter
import com.example.movilesfinalrestaurante.databinding.ActivityMenuBinding
import com.example.movilesfinalrestaurante.models.MenuCategory
import com.example.movilesfinalrestaurante.models.Plate
import com.example.movilesfinalrestaurante.repositories.RestaurantRepository

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private val categories = mutableListOf<MenuCategory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mostrar el mensaje de carga
        binding.progressBar.visibility = View.VISIBLE
        binding.tvMessage.text = "Cargando menú"
        binding.tvMessage.visibility = View.VISIBLE

        // Obtener el menú desde la API
        getMenu()
    }

    private fun getMenu() {
        val restaurantId = intent.getIntExtra("RESTAURANT_ID", 0) //obtiene el id del restaurante
        RestaurantRepository.getMenu(restaurantId, success = { menuCategories ->
            binding.progressBar.visibility = View.GONE //desaparece cuando ya lo tiene
            binding.tvMessage.visibility = View.GONE

            if (menuCategories.isNullOrEmpty()) { //si no hay menu
                binding.tvMessage.text = "No existe un menú"
                binding.tvMessage.visibility = View.VISIBLE
            } else {
                categories.clear()
                categories.addAll(menuCategories)
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.recyclerView.adapter = MenuAdapter(categories, ::onPlateSelected)
                binding.recyclerView.visibility = View.VISIBLE
            }
        }, failure = { t ->
            binding.progressBar.visibility = View.GONE
            binding.tvMessage.text = "Error: ${t.message}"
            binding.tvMessage.visibility = View.VISIBLE
            Toast.makeText(this, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
        })
    }

    private fun onPlateSelected(plate: Plate, isSelected: Boolean) {
        // Implementar la lógica cuando un plato es seleccionado
        if (isSelected) {
            Toast.makeText(this, "${plate.name} seleccionado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "${plate.name} deseleccionado", Toast.LENGTH_SHORT).show()
        }
    }
}