package com.latoin.ve.stlator.calculator.one.calculatorViewModel

import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    private var investment = 0.0
    private var period = 0

    fun setInvestment(value: Double) {
        investment = value
    }

    fun setPeriod(value: Int) {
        period = value
    }

    fun calculatePeriodInDays(): Int {
        return period
    }

    fun calculateInvestmentResult(): Double {
        return investment
    }
}
