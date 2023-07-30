package com.example.pizzaapp.api

import com.example.pizzaapp.beans.PizzaBean
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(ApiConstants.api)
    suspend fun getData(): Response<PizzaBean?>
}