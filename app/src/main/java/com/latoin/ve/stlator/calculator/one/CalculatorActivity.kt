package com.latoin.ve.stlator.calculator.one

import com.latoin.ve.stlator.calculator.one.calculatorViewModel.CalculatorViewModel
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

@Suppress("DEPRECATION")
class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding
    private lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]
        initListener()

    }

    private fun initListener() {
        radioButton()

        binding.btnNext.setOnClickListener {
            if (binding.edInvestment.text.toString().isNotEmpty() && binding.edPeriod.text.toString().isNotEmpty()) {

                viewModel.setInvestment(binding.edInvestment.text.toString().toDouble())
                viewModel.setPeriod(binding.edPeriod.text.toString().toInt())

                navigateTo()

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

        }

        selectedButton = binding.btnY
        selectedButton?.setTextColor(ContextCompat.getColor(this, R.color.black))
        selectedButton?.setBackgroundResource(R.drawable.radio_btn_selected)


        for (button in radioButtons) {
            button.setOnClickListener { onRadioButtonClicked(it) }
        }
    }

    private fun navigateTo() {
        Intent(this, CalculateTwoActivity::class.java).apply {
            putExtra(KEY_INVESTMENT, viewModel.calculateInvestmentResult())
            putExtra(KEY_PERIOD_ONE, viewModel.calculatePeriodInDays())
            startActivity(this)
            onBackPressed()
        }
    }

    companion object{
        const val KEY_INVESTMENT = "investment"
        const val KEY_PERIOD_ONE = "period"
    }

}