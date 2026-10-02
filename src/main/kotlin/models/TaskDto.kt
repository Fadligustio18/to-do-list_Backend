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
)
