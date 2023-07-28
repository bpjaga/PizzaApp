package com.example.pizzaapp.beans

import com.google.gson.annotations.SerializedName

data class CrustBean(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("defaultSize")
    var defaultSize: Int,
    @SerializedName("sizes")
    var sizes: List<SizeBean>
) {
}