package com.example.movilesfinalrestaurante.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.movilesfinalrestaurante.R
import com.example.movilesfinalrestaurante.databinding.ActivityFotoBinding

class FotoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtiene la URL de la foto desde los extras del intent
        val photoUrl = intent.getStringExtra("PHOTO_URL")
        photoUrl?.let {
            Glide.with(this).load(it).into(binding.photoView)
        }
    }
}