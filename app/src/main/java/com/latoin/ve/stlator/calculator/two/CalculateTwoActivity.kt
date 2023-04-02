package com.latoin.ve.stlator.calculator.two

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.latoin.ve.stlator.R
import com.latoin.ve.stlator.calculator.one.CalculatorActivity.Companion.KEY_INVESTMENT
import com.latoin.ve.stlator.calculator.one.CalculatorActivity.Companion.KEY_PERIOD_ONE
import com.latoin.ve.stlator.calculator.result.ResultCalculatorActivity
import com.latoin.ve.stlator.databinding.ActivityCalculateTwoBinding
import com.latoin.ve.stlator.ext.showToast

@Suppress("DEPRECATION")
class CalculateTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculateTwoBinding
    private lateinit var viewModel: CalculatorTwoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CalculatorTwoViewModel::class.java]
        initListener()
        getData()

    }

    private fun getData(){
        viewModel.investment = intent.getDoubleExtra(KEY_INVESTMENT, 0.0)
        viewModel.period = intent.getIntExtra(KEY_PERIOD_ONE, 0)
    }

    private fun initListener() {
        radioButton()
        binding.btnCalculate.setOnClickListener {
            if (binding.edPercent.text.toString().isNotEmpty()) {
                val percentValue = binding.edPercent.text.toString().toDoubleOrNull()
                viewModel.percent = binding.edPercent.text.toString().toDouble()
                if (percentValue != null) {
                    val result = if (viewModel.percentType == KEY_SIMPLE) {
                        viewModel.calculateSimpleInterest()
                    } else {
                        viewModel.calculateCompoundInterest()
                    }

                    navigateTo(result)
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
                KEY_SIMPLE
            } else {
                KEY_COMP
            }
        }

        selectedButton = binding.btnC
        selectedButton?.setTextColor(ContextCompat.getColor(this, R.color.black))
        selectedButton?.setBackgroundResource(R.drawable.radio_btn_selected)
        viewModel.percentType = KEY_COMP
        for (button in radioButtons) {
            button.setOnClickListener { onRadioButtonClicked(it) }
        }

        when (selectedButton) {
            binding.btnSimple -> {
                viewModel.percentType = KEY_SIMPLE
            }
            binding.btnC -> {
                viewModel.percentType = KEY_COMP

            }
        }
    }

    private fun navigateTo(result: Double) {
        Intent(this, ResultCalculatorActivity::class.java).apply {
            putExtra(KEY_INVEST, viewModel.investment)
            putExtra(KEY_PERIOD, viewModel.period)
            putExtra(KEY_RESULT, result)
            putExtra(KEY_PERCENT_TYPE, viewModel.percentType)
            startActivity(this)
            onBackPressed()
        }
    }

    companion object{
        const val KEY_INVEST = "invest"
        const val KEY_PERIOD = "period"
        const val KEY_RESULT = "result"
        const val KEY_PERCENT_TYPE = "percent_type"
        const val KEY_SIMPLE = "simple"
        const val KEY_COMP = "comp"
    }
}

