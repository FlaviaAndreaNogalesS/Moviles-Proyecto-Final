package com.example.movilesfinalrestaurante.activities.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movilesfinalrestaurante.models.User
import com.example.movilesfinalrestaurante.repositories.UserRepository

class RegisterViewModel : ViewModel() {
    private val _successMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val successMessage: LiveData<String> get() = _successMessage

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    fun registerUser(email: String, password: String, fullName: String, phone: String, context: Context) {
        val user = User(email, password, fullName, phone)
        UserRepository.registerUser(user, success = { response ->
            response?.let {
                if (it.success) {
                    _successMessage.value = "Registro exitoso"
                } else {
                    _errorMessage.value = it.message
                }
            }
        }, failure = { t ->
            when {
                t.message?.contains("400") == true -> _errorMessage.value = "El usuario ya existe"
                else -> _errorMessage.value = "Error: ${t.message}"
            }
        })
    }
}