package com.example.pizzaapp.ui.activity

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzaapp.api.Envelope
import com.example.pizzaapp.api.ErrorModel
import com.example.pizzaapp.beans.PizzaBean
import com.example.pizzaapp.repository.Repository
import com.example.pizzaapp.ui.models.OrderModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: Repository
) : ViewModel() {


    var pizzaDetails = PizzaBean()
    private val _orders = MutableLiveData<List<OrderModel>>(null)
    val orders get() = _orders
    val progressBoolean = ObservableBoolean(false)
    val error = MutableStateFlow<ErrorModel?>(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getData().collectLatest {
                when (it) {
                    is Envelope.Loading -> progressBoolean.set(true)
                    is Envelope.Error -> error.value = it.error
                    is Envelope.Success -> pizzaDetails = it.data
                }
                progressBoolean.set(false)
            }
        }
    }

    fun addToCart(orderModel: OrderModel) {
        var pizzaList = _orders.value
        val ord =
            pizzaList?.find { it.crust.id == orderModel.crust.id && it.size.id == orderModel.size.id }
        ord?.apply {
            quantity += 1
        }
        if (ord == null) {
            pizzaList = arrayListOf<OrderModel>().apply {
                pizzaList?.let { addAll(it) }
                add(orderModel.apply { quantity = 1 })
            }
        }
        _orders.postValue(pizzaList ?: listOf())
    }


    fun removeFromCart(orderModel: OrderModel) {
        var pizzaList = _orders.value
        pizzaList?.let { orderList ->
            val order =
                orderList.find { it.crust.id == orderModel.crust.id && it.size.id == orderModel.size.id }
            order?.let { ord ->
                ord.quantity -= 1
                if (ord.quantity <= 0)
                    pizzaList =
                        orderList.filterNot { it.crust.id == orderModel.crust.id && it.size.id == orderModel.size.id }
            }
        }
        _orders.postValue(pizzaList ?: listOf())
    }

    fun deleteFromCart(crustId: Int, sizeId: Int) {
        var pizzaList = _orders.value
        pizzaList?.let { ord ->
            pizzaList = ord.filterNot { it.crust.id == crustId && it.size.id == sizeId }
        }
        _orders.postValue(pizzaList ?: listOf())
    }

}