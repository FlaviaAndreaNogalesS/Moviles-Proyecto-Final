package com.example.movilesfinalrestaurante.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movilesfinalrestaurante.R
import com.example.movilesfinalrestaurante.models.MenuCategory
import com.example.movilesfinalrestaurante.models.Plate

class MenuAdapter(
    private val categories: List<MenuCategory>,
    private val onPlateSelected: (Plate, Boolean) -> Unit
) : RecyclerView.Adapter<MenuAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName: TextView = view.findViewById(R.id.categoryName)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        fun bind(category: MenuCategory, onPlateSelected: (Plate, Boolean) -> Unit) {
            categoryName.text = category.name
            recyclerView.layoutManager = LinearLayoutManager(itemView.context)
            recyclerView.adapter = PlateAdapter(category.plates, onPlateSelected)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], onPlateSelected)
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}