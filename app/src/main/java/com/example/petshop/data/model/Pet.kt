package com.example.petshop.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Pet(
    val id: String,
    val name: String,
    val description: String,
    val age: Int,
    val type: PetType,
    var adopted: Boolean = false,
    val photoLink: String,
)