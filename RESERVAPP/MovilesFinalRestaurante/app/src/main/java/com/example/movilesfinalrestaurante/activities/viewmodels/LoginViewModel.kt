package com.example.movilesfinalrestaurante.activities.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movilesfinalrestaurante.models.dto.LoginRequestDTO
import com.example.movilesfinalrestaurante.repositories.PreferencesRepository
import com.example.movilesfinalrestaurante.repositories.UserRepository

class LoginViewModel : ViewModel() {
    private val _successMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val successMessage: LiveData<String> get() = _successMessage

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    fun loginUser(email: String, password: String, context: Context) {
        val user = LoginRequestDTO(email, password)
        UserRepository.doLogin(email, password, success = { response ->
            response?.let {
                if (it.access_token != null) {
                    _successMessage.value = "Inicio de sesión exitoso"
                    PreferencesRepository.saveToken(it.access_token, context) //guarda el token
                } else {
                    _errorMessage.value = "Usuario o contraseña incorrectos"
                }
            }
        }, failure = { t ->
            _errorMessage.value = "Error: ${t.message}"
        })
    }
}