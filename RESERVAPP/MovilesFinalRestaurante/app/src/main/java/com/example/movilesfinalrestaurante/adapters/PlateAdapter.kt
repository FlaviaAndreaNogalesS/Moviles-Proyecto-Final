package com.example.movilesfinalrestaurante.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movilesfinalrestaurante.R
import com.example.movilesfinalrestaurante.models.Plate

class PlateAdapter(
    private val plates: List<Plate>,
    private val onPlateSelected: (Plate, Boolean) -> Unit
) : RecyclerView.Adapter<PlateAdapter.PlateViewHolder>() {

    class PlateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plateName: TextView = view.findViewById(R.id.plateName)
        val plateDescription: TextView = view.findViewById(R.id.plateDescription)
        val platePrice: TextView = view.findViewById(R.id.platePrice)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)

        fun bind(plate: Plate, onPlateSelected: (Plate, Boolean) -> Unit) {
            plateName.text = plate.name
            plateDescription.text = plate.description
            platePrice.text = "Bs: ${plate.price}"
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                onPlateSelected(plate, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plate, parent, false)
        return PlateViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlateViewHolder, position: Int) {
        holder.bind(plates[position], onPlateSelected)
    }

    override fun getItemCount(): Int {
        return plates.size
    }
}