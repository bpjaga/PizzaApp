package com.example.pizzaapp.Utils

import androidx.databinding.BindingAdapter
import com.example.pizzaapp.R
import com.example.pizzaapp.ui.models.OrderListModel
import com.google.android.material.textview.MaterialTextView

@BindingAdapter("setPrice")
fun MaterialTextView.setPrice(model: OrderListModel) {
    text = String.format(context.getString(R.string.rupee_2f), model.price * model.quantity)
}

