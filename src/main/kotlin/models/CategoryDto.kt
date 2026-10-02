package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Int? = null,
    val name: String
)