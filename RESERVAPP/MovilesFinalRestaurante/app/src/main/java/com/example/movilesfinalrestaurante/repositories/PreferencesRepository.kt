package com.example.movilesfinalrestaurante.repositories

import android.content.Context

object PreferencesRepository {

    // Guarda el token de autenticación
    fun saveToken(token: String, context: Context?) {
        val sharedPref = context?.getSharedPreferences("proyecto-final", Context.MODE_PRIVATE)
        with(sharedPref?.edit()) {
            this?.putString("token", token)
            this?.apply()
        }
    }

    // Obtiene el token de autenticación
    fun getToken(context: Context?): String? {
        val sharedPref = context?.getSharedPreferences("proyecto-final", Context.MODE_PRIVATE)
        return sharedPref?.getString("token", null)
    }

    // Elimina el token de autenticación
    fun clearToken(context: Context?) {
        val sharedPref = context?.getSharedPreferences("proyecto-final", Context.MODE_PRIVATE)
        with(sharedPref?.edit()) {
            this?.remove("token")
            this?.apply()
        }
    }

    // Guarda el nombre de usuario (aunque esto no me funciona xd)
    fun saveUserName(name: String, context: Context?) {
        val sharedPref = context?.getSharedPreferences("proyecto-final", Context.MODE_PRIVATE)
        with(sharedPref?.edit()) {
            this?.putString("user_name", name)
            this?.apply()
        }
    }

    // Obtiene el nombre de usuario
    fun getUserName(context: Context?): String? {
        val sharedPref = context?.getSharedPreferences("proyecto-final", Context.MODE_PRIVATE)
        return sharedPref?.getString("user_name", null)
    }
}
