package com.example.pizzaapp.ui.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import com.example.pizzaapp.R
import com.example.pizzaapp.databinding.FragmentCustomizeBinding
import com.example.pizzaapp.ui.activity.MainViewModel
import com.example.pizzaapp.ui.models.OrderModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CustomizeFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentCustomizeBinding
    private val viewModel: MainViewModel by activityViewModel()
    private val crusts: ArrayList<Int> = arrayListOf()
    private val sizes: ArrayList<Int> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customize, container, false)
        binding.onClick = this

        binding.crust.setOnCheckedChangeListener { _, checkedId ->
            sizes.clear()
            binding.size.removeAllViews()
            binding.size.invalidate()
            binding.sizeTv.visibility = View.VISIBLE
            binding.size.visibility = View.VISIBLE
            val crust = viewModel.pizzaDetails.crusts[crusts.indexOf(checkedId)]
            crust.sizes.forEach {
                val rb = RadioButton(requireContext())
                rb.id = View.generateViewId()
                rb.text = String.format(getString(R.string.size_s_price_2f), it.name, it.price)
                rb.tag = it.id
                if (it.id == crust.defaultSize)
                    rb.isChecked = true
                sizes.add(rb.id)
                binding.size.addView(rb)
            }
        }

        crusts.clear()
        binding.crust.removeAllViews()
        viewModel.pizzaDetails.crusts.forEach {
            val rb = RadioButton(requireContext())
            rb.id = View.generateViewId()
            rb.text = it.name
            rb.tag = it.id
            if (it.id == viewModel.pizzaDetails.defaultCrust)
                rb.isChecked = true
            crusts.add(rb.id)
            binding.crust.addView(rb)
        }
        return binding.root
    }

    override fun onClick(v: View?) {
        val crust = viewModel.pizzaDetails.crusts[crusts.indexOf(
            binding.crust.checkedRadioButtonId
        )]
        val size = crust.sizes[sizes.indexOf(binding.size.checkedRadioButtonId)]
        viewModel.addToCart(
            OrderModel(
                crust = crust,
                size = size
            )
        )
        dismiss()
    }

}