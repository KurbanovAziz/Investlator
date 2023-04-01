package com.latoin.ve.stlator.calculator.one

import CalculatorViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.latoin.ve.stlator.R
import com.latoin.ve.stlator.calculator.two.CalculateTwoActivity
import com.latoin.ve.stlator.databinding.ActivityCalculatorBinding
import com.latoin.ve.stlator.ext.showToast

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding
    private lateinit var viewModel: CalculatorViewModel
    private var period = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]
        initListener()

    }

    private fun initListener() {
        binding.btnY.setOnClickListener {
            viewModel.setSelectedButtonId(R.id.btn_y)
            binding.btnY.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.btnY.setBackgroundResource(R.drawable.radio_btn_selected)
            binding.btnM.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.btnM.setBackgroundResource(R.drawable.radio_btn)
        }

        binding.btnM.setOnClickListener {
            viewModel.setSelectedButtonId(R.id.btn_m)
            binding.btnM.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.btnM.setBackgroundResource(R.drawable.radio_btn_selected)
            binding.btnY.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.btnY.setBackgroundResource(R.drawable.radio_btn)
        }

        binding.btnNext.setOnClickListener {
            if (binding.edInvestment.text.toString().isNotEmpty() && binding.edPeriod.text.toString().isNotEmpty()) {
                viewModel.setInvestment(binding.edInvestment.text.toString().toDouble())
                viewModel.setPeriod(binding.edPeriod.text.toString().toInt())

                Intent(this, CalculateTwoActivity::class.java).apply {
                    putExtra("investment", viewModel.calculateInvestmentResult())
                    putExtra("period", viewModel.calculatePeriodInDays())
                    startActivity(this)
                }
            } else {
                showToast(getString(R.string.all_fields_must_be_filled))
            }
        }

    }

    private fun radioButton() {
        val radioButtons = arrayOf(binding.btnY, binding.btnM)

        var selectedButton: Button? = null

        fun onRadioButtonClicked(view: View) {
            selectedButton?.let {
                it.setTextColor(ContextCompat.getColor(this, R.color.white))
                it.setBackgroundResource(R.drawable.radio_btn)
            }
            selectedButton = view as Button
            selectedButton?.setTextColor(ContextCompat.getColor(this, R.color.black))
            selectedButton?.setBackgroundResource(R.drawable.radio_btn_selected)
            period *= if (selectedButton == binding.btnY) {
                365
            } else {
                30
            }
        }

        for (button in radioButtons) {
            button.setOnClickListener { onRadioButtonClicked(it) }
        }

        when (selectedButton) {
            binding.btnY -> {
                period *= 365
            }
            binding.btnM -> {
                period *= 365
            }
            else -> {
                // Ни один элемент не выбран
            }
        }
    }

}