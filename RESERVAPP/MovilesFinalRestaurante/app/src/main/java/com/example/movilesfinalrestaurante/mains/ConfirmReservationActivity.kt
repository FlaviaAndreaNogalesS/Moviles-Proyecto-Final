package com.example.movilesfinalrestaurante.mains

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movilesfinalrestaurante.R
import com.example.movilesfinalrestaurante.databinding.ActivityConfirmReservationBinding

class ConfirmReservationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmReservationBinding
    private var restaurantId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtiene el id
        restaurantId = intent.getIntExtra("RESTAURANT_ID", 0)
        if (restaurantId == 0) {
            Toast.makeText(this, "Error: Restaurante no encontrado", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnConfirm.setOnClickListener {
            val date = binding.etDate.text.toString().trim()
            val time = binding.etTime.text.toString().trim()

            if (date.isNotEmpty() && time.isNotEmpty()) {
                //pasa los datos ingresados
                val intent = Intent(this, MenuSelectionActivity::class.java)
                intent.putExtra("RESTAURANT_ID", restaurantId)
                intent.putExtra("RESERVATION_DATE", date)
                intent.putExtra("RESERVATION_TIME", time)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, ingrese la fecha y la hora", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


