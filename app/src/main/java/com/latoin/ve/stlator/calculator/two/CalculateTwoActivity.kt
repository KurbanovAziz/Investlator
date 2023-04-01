package com.latoin.ve.stlator.calculator.two

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.latoin.ve.stlator.R
import com.latoin.ve.stlator.calculator.result.ResultCalculatorActivity
import com.latoin.ve.stlator.databinding.ActivityCalculateTwoBinding
import com.latoin.ve.stlator.ext.showToast

class CalculateTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculateTwoBinding
    private lateinit var viewModel: CalculatorTwoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CalculatorTwoViewModel::class.java]
        initListener()


        viewModel.investment = intent.getDoubleExtra("investment", 0.0)
        viewModel.period = intent.getIntExtra("period", 0)
    }

    private fun initListener() {
        radioButton()
        binding.btnCalculate.setOnClickListener {
            if (binding.edPercent.text.toString().isNotEmpty()) {
                val percentValue = binding.edPercent.text.toString().toDoubleOrNull()
                viewModel.percent = binding.edPercent.text.toString().toDouble()
                if (percentValue != null) {
                    val result = if (viewModel.percentType == "simple") {
                        viewModel.calculateSimpleInterest()
                    } else {
                        viewModel.calculateCompoundInterest()
                    }
                    Intent(this, ResultCalculatorActivity::class.java).apply {
                        putExtra("invest", viewModel.investment)
                        putExtra("period", viewModel.period)
                        putExtra("percent", viewModel.percent)
                        putExtra("result", result)
                        putExtra("percent_type", viewModel.percentType)
                        startActivity(this)
                    }
                }
            } else {
                showToast(getString(R.string.all_fields_must_be_filled))
            }
        }

    }

    private fun radioButton() {
        val radioButtons = arrayOf(binding.btnSimple, binding.btnC)

        var selectedButton: Button? = null

        fun onRadioButtonClicked(view: View) {
            selectedButton?.let {
                it.setTextColor(ContextCompat.getColor(this, R.color.white))
                it.setBackgroundResource(R.drawable.radio_btn)
            }
            selectedButton = view as Button
            selectedButton?.setTextColor(ContextCompat.getColor(this, R.color.black))
            selectedButton?.setBackgroundResource(R.drawable.radio_btn_selected)

            viewModel.percentType = if (selectedButton == binding.btnSimple) {
                "simple"
            } else {
                "comp"
            }
        }

        for (button in radioButtons) {
            button.setOnClickListener { onRadioButtonClicked(it) }
        }

        when (selectedButton) {
            binding.btnSimple -> {
                viewModel.percentType = "simple"
            }
            binding.btnC -> {
                viewModel.percentType = "comp"
            }
            else -> {
                // Ни один элемент не выбран
            }
        }
    }
}

