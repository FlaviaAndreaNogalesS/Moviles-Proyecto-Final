package com.example.movilesfinalrestaurante.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movilesfinalrestaurante.R
import com.example.movilesfinalrestaurante.models.Reservation

class ReservationsAdapter(
    private val reservations: List<Reservation>,
    private val onItemClick: (Reservation) -> Unit
) : RecyclerView.Adapter<ReservationsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivRestaurantLogo: ImageView = view.findViewById(R.id.ivRestaurantLogo)
        val tvRestaurantName: TextView = view.findViewById(R.id.tvRestaurantName)
        val tvReservationDate: TextView = view.findViewById(R.id.tvReservationDate)
        val tvReservationTime: TextView = view.findViewById(R.id.tvReservationTime)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val iconStatus: ImageView = view.findViewById(R.id.imgStatus)

        fun bind(reservation: Reservation, onItemClick: (Reservation) -> Unit) {
            Glide.with(itemView.context).load(reservation.restaurant.logo).into(ivRestaurantLogo)
            tvRestaurantName.text = reservation.restaurant.name
            tvReservationDate.text = reservation.date
            tvReservationTime.text = reservation.time
            tvStatus.text = reservation.status
            iconStatus.setImageResource(R.drawable.ic_status)
            itemView.setOnClickListener { onItemClick(reservation) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reservations[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return reservations.size
    }
}

