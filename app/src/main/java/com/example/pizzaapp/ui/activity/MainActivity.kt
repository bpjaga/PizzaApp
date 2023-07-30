package com.example.pizzaapp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pizzaapp.R
import com.example.pizzaapp.databinding.ActivityMainBinding
import com.example.pizzaapp.ui.activity.fragments.CustomizeFragment
import com.example.pizzaapp.ui.adapters.PizzaOrderAdapter
import com.example.pizzaapp.ui.dialog.ErrorDialog
import com.example.pizzaapp.ui.models.OrderListModel
import com.example.pizzaapp.ui.models.OrderModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: PizzaOrderAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.onClick = this
        adapter = PizzaOrderAdapter(object : PizzaOrderAdapter.ClickInterface {
            override fun onClick(model: OrderListModel) {
                val crust = viewModel.pizzaDetails.crusts.find { model.crustId == it.id }
                val size = crust?.sizes?.find { model.sizeId == it.id }
                if (crust != null && size != null) {
                    if (model.quantity == 1)
                        viewModel.addToCart(OrderModel(crust, size))
                    else
                        viewModel.removeFromCart(OrderModel(crust, size))
                }

            }

            override fun onDelete(orderListModel: OrderListModel) {
                viewModel.deleteFromCart(orderListModel.crustId, orderListModel.sizeId)
            }

        })
        binding.itemsRv.adapter = adapter

        lifecycleScope.launch {
            viewModel.error.collectLatest {
                it?.let { ErrorDialog(it).show(supportFragmentManager, "") }
            }
        }

        viewModel.orders.observe(this) {
            val ordersList = arrayListOf<OrderListModel>()
            var totalQuantity = 0
            var totalPrice = 0F
            it?.forEach { ord ->
                ordersList.add(
                    OrderListModel(
                        viewModel.pizzaDetails.name,
                        viewModel.pizzaDetails.description,
                        viewModel.pizzaDetails.isVeg,
                        ord.crust.id,
                        ord.crust.name,
                        ord.size.id,
                        ord.size.name,
                        ord.size.price,
                        ord.quantity
                    )
                )
                totalQuantity += ord.quantity
                totalPrice += ord.size.price * ord.quantity
            }
            binding.count.text = totalQuantity.toString()
            binding.totalPrice.text = String.format(getString(R.string.rupee_2f), totalPrice)
            adapter.submitList(ordersList.toList())
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addPizza -> {
                if (viewModel.pizzaDetails.crusts.isNotEmpty())
                    CustomizeFragment().show(supportFragmentManager, "")
            }

            else -> {}
        }
    }
}