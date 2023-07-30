package com.example.pizzaapp.repository

import com.example.pizzaapp.api.Envelope
import com.example.pizzaapp.beans.PizzaBean
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getData(): Flow<Envelope<PizzaBean>>
}