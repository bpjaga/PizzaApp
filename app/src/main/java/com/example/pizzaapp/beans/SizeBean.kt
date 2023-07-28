package com.example.pizzaapp.beans

import com.google.gson.annotations.SerializedName

data class SizeBean(
@SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("price")
    var price: Float
)