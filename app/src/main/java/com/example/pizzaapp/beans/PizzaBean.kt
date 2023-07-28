package com.example.pizzaapp.beans

import com.google.gson.annotations.SerializedName

data class PizzaBean(
    @SerializedName("name")
    var name: String,
    @SerializedName("isVeg")
    var isVeg: Boolean,
    @SerializedName("description")
    var description: String,
    @SerializedName("defaultCrust")
    var defaultCrust: Int,
    @SerializedName("crusts")
    var crusts: List<CrustBean>
)