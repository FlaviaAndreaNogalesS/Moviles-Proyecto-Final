package com.example.movilesfinalrestaurante.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.movilesfinalrestaurante.R
import com.example.movilesfinalrestaurante.activities.viewmodels.RegisterViewModel
import com.example.movilesfinalrestaurante.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    val model: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView2.setImageResource(R.drawable.logo)
        setupEventListeners()
        setupViewModelObservers()
    }

    private fun setupEventListeners() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim() //elimina los espacios en blanco
            val password = binding.etPassword.text.toString().trim()
            val fullName = binding.etFullName.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()

            // Verifica que todos los campos estén llenos
            if (email.isNotEmpty() && password.isNotEmpty() && fullName.isNotEmpty() && phone.isNotEmpty()) {
                model.registerUser(email, password, fullName, phone, this)
            } else {
                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViewModelObservers() {
        model.successMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                if (it == "Registro exitoso") {
                    val intent = Intent(this, LoginActivity::class.java) //si es exitoso
                    startActivity(intent)
                    finish() //finaliza
                }
            }
        }

        model.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
