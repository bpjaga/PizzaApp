package com.example.pizzaapp.ui.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pizzaapp.beans.CrustBean
import com.example.pizzaapp.beans.PizzaBean
import com.example.pizzaapp.beans.SizeBean
import com.example.pizzaapp.repository.Repository
import com.example.pizzaapp.ui.models.OrderModel

class MainViewModel(
    private val repo: Repository
) : ViewModel() {


    var pizzaDetails = PizzaBean()
    private val _orders = MutableLiveData<List<OrderModel>>(null)
    val orders get() = _orders

    init {
        //repo.getPizza()
        pizzaDetails = PizzaBean(
            "abc",
            false,
            "lorem epsum",
            1,
            arrayListOf(
                CrustBean(
                    1,
                    "sample1",
                    2,
                    arrayListOf(
                        SizeBean(
                            1,
                            "small",
                            25.12F
                        ),
                        SizeBean(
                            2,
                            "med",
                            45.12F
                        ),
                        SizeBean(
                            3,
                            "large",
                            135.12F
                        )
                    )
                ),
                CrustBean(
                    2,
                    "sample2",
                    1,
                    arrayListOf(
                        SizeBean(
                            1,
                            "med",
                            65.12F
                        ),
                        SizeBean(
                            2,
                            "large",
                            75.12F
                        )
                    )
                )
            )
        )
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