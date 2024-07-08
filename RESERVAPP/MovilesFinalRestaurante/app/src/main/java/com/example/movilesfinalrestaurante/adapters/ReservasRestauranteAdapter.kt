package com.example.movilesfinalrestaurante.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.movilesfinalrestaurante.R
import com.example.movilesfinalrestaurante.models.Reservation

class ReservasRestauranteAdapter(
    private val reservations: List<Reservation>,
    private val onCancelClick: (Reservation) -> Unit,
    private val onConfirmClick: (Reservation) -> Unit
) : RecyclerView.Adapter<ReservasRestauranteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRestaurantId: TextView = view.findViewById(R.id.tvRestaurantId)
        val tvReservationDate: TextView = view.findViewById(R.id.tvReservationDate)
        val tvReservationTime: TextView = view.findViewById(R.id.tvReservationTime)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val btnCancel: Button = view.findViewById(R.id.btnCancel)
        val btnConfirm: Button = view.findViewById(R.id.btnConfirm)

        fun bind(reservation: Reservation, onCancelClick: (Reservation) -> Unit, onConfirmClick: (Reservation) -> Unit) {
            tvRestaurantId.text = "Restaurante ID: ${reservation.restaurant_id}"
            tvReservationDate.text = reservation.date
            tvReservationTime.text = reservation.time
            tvStatus.text = reservation.status

            btnCancel.setOnClickListener { onCancelClick(reservation) }
            btnConfirm.setOnClickListener { onConfirmClick(reservation) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reservation_restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reservations[position], onCancelClick, onConfirmClick)
    }

    override fun getItemCount(): Int {
        return reservations.size
    }
}
