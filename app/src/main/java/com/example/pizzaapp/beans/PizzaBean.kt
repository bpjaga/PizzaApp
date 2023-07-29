package com.example.pizzaapp.beans

import com.google.gson.annotations.SerializedName

data class PizzaBean(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("isVeg")
    var isVeg: Boolean = false,
    @SerializedName("description")
    var description: String = "",
    @SerializedName("defaultCrust")
    var defaultCrust: Int = 0,
    @SerializedName("crusts")
    var crusts: List<CrustBean> = listOf()
)