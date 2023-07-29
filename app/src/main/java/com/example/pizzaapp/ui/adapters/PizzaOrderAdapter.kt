package com.example.pizzaapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaapp.R
import com.example.pizzaapp.databinding.ItemPizzaOrderBinding
import com.example.pizzaapp.ui.models.OrderListModel

class PizzaOrderAdapter(
    private val clickInterface: ClickInterface
) : ListAdapter<OrderListModel, PizzaOrderAdapter.ViewHolder>(Callback()) {
    inner class ViewHolder(
        private val binding: ItemPizzaOrderBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(model: OrderListModel) {
            binding.model = model
            binding.onClick = this
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.addQuantity -> clickInterface.onClick(binding.model?.apply { quantity = 1 }
                    ?: OrderListModel())

                R.id.reduceQuantity -> clickInterface.onClick(binding.model?.apply { quantity = -1 }
                    ?: OrderListModel())

                R.id.deletePizza -> clickInterface.onDelete(binding.model?: OrderListModel())

                else -> {}
            }
        }
    }

    class Callback : DiffUtil.ItemCallback<OrderListModel>() {
        override fun areItemsTheSame(oldItem: OrderListModel, newItem: OrderListModel): Boolean {
            return oldItem.crustId == newItem.crustId && oldItem.sizeId == newItem.sizeId && oldItem.quantity == newItem.quantity
        }

        override fun areContentsTheSame(oldItem: OrderListModel, newItem: OrderListModel): Boolean {
            return oldItem.crustId == newItem.crustId && oldItem.sizeId == newItem.sizeId && oldItem.quantity == newItem.quantity
        }

    }

    interface ClickInterface {
        fun onClick(model: OrderListModel)
        fun onDelete(orderListModel: OrderListModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemPizzaOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}