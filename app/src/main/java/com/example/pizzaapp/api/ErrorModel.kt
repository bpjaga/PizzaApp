package com.example.pizzaapp.api

data class ErrorModel(
    val errorCode: Int,
    val message: String = ""
)