package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(val id: Int? = null, val name: String){
fun validate(): String?{
    if(name.isBlank()) return "name category tidak boleh kosong"
    if(name.length > 100) return "name category maksimal 100kata"
    return null
}
}