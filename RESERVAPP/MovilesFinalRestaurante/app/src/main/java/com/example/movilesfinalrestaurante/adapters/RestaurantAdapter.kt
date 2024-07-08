package com.example.movilesfinalrestaurante.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movilesfinalrestaurante.R
import com.example.movilesfinalrestaurante.models.Restaurant

class RestaurantAdapter(
    private val restaurants: List<Restaurant>,
    private val clickListener: (Restaurant) -> Unit) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.restaurantName)
        val city: TextView = view.findViewById(R.id.restaurantCity)
        val description: TextView = view.findViewById(R.id.restaurantDescription)
        val logo: ImageView = view.findViewById(R.id.restaurantLogo)

        fun bind(restaurant: Restaurant, clickListener: (Restaurant) -> Unit) {
            name.text = restaurant.name
            city.text = restaurant.city
            description.text = restaurant.description
            Glide.with(itemView.context).load(restaurant.logo).into(logo)
            itemView.setOnClickListener { clickListener(restaurant) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurants[position], clickListener)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }
}
