package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDto (
    val id: Int? = null,
    val email: String,
    val password: String
    ){
    fun validate(): String? {
        if (email.isBlank()) return "Email tidak boleh kosong"
        if (!email.contains("@")) return "Format email tidak valid"
        if (password.length < 6) return "Password Minimal 6 karakter"
        return null
    }
}