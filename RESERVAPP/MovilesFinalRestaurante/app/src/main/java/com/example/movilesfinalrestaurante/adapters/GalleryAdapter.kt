package com.example.movilesfinalrestaurante.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movilesfinalrestaurante.models.Photo
import com.example.movilesfinalrestaurante.R

class GalleryAdapter(private val photos: List<Photo>, private val clickListener: (String) -> Unit) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)

        fun bind(photo: Photo, clickListener: (String) -> Unit) {
            Glide.with(itemView.context).load(photo.url).into(imageView)
            itemView.setOnClickListener {
                clickListener(photo.url)
                Toast.makeText(itemView.context, "Foto cargada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position], clickListener)
        Toast.makeText(holder.itemView.context, "Cargando fotos", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return photos.size
    }
}
