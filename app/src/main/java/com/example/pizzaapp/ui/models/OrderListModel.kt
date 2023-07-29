package com.example.pizzaapp.ui.models

import androidx.databinding.BaseObservable

data class OrderListModel(
    val pizzaName: String = "",
    val pizzaDescription: String = "",
    val isVeg: Boolean = false,
    val crustId: Int = 0,
    val crustName: String = "",
    val sizeId: Int = 0,
    val sizeName: String = "",
    val price: Float = 0F,
    var quantity: Int = 0
) : BaseObservable()