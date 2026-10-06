package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
     val id: Int? = null,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val categoryId: Int,
    val categoryName: String? = null
){
    fun validate(): String?{
        if (title.isBlank()) return "title tidak boleh kosong"
        if (title.length > 100) return "title maksimal 100 karakter"
        if (categoryId <= 0) return "categoryId tidak valid"
        return null
    }
}
