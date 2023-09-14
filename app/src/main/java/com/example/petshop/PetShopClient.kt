package com.example.petshop

import com.example.petshop.data.model.Pet
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


const val API_URL = "http://10.0.2.2:8080/api/pets"

class PetShopClient {

    private val client = HttpClient(Android) {

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun getPets(): List<Pet> {
        val response = client.get(API_URL)
        return response.body()
    }
}