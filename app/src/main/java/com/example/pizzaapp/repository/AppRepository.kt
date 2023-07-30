package com.example.pizzaapp.repository

import com.example.pizzaapp.api.ApiService
import com.example.pizzaapp.api.Envelope
import com.example.pizzaapp.api.ErrorModel
import com.example.pizzaapp.beans.PizzaBean
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppRepository(
    private val api: ApiService
) : Repository {
    override suspend fun getData(): Flow<Envelope<PizzaBean>> = flow {
        emit(Envelope.loading())
        try {
            val response = api.getData()
            if (response.isSuccessful)
                response.body()?.let {
                    emit(Envelope.success(it))
                }
            else
                response.errorBody()?.let {
                    emit(Envelope.error(ErrorModel(errorCode = response.code())))
                }
        } catch (e: Exception) {
            emit(Envelope.error(ErrorModel(503, e.message ?: "")))
        }
    }

}