package com.example.autoclicker_game

import retrofit2.Call
import retrofit2.http.GET

// Data class to hold the response
data class CatFact(val fact: String)

// Retrofit interface for API calls
interface CatFactApi {
    @GET("fact")
    fun getCatFact(): Call<CatFact>
}