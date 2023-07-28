package com.example.pizzaapp.repository

import com.example.pizzaapp.api.ApiService

class AppRepository(
    private val api: ApiService
) : Repository {
}