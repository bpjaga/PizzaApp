package com.example.pizzaapp.ui.models

import androidx.databinding.BaseObservable
import com.example.pizzaapp.beans.CrustBean
import com.example.pizzaapp.beans.SizeBean

data class OrderModel(
    var crust: CrustBean,
    var size: SizeBean,
    var quantity: Int = 0
) : BaseObservable()
