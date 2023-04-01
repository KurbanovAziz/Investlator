package com.latoin.ve.stlator.calculator.two
import androidx.lifecycle.ViewModel
import kotlin.math.pow

class CalculatorTwoViewModel : ViewModel() {


    var investment: Double = 0.0
    var period: Int = 0
    var percentType: String = ""
    var percent: Double = 0.0

    fun calculateCompoundInterest(): Double {
        val decimalPercent = percent / 100.0
        val compoundInterest = investment * (1.0 + decimalPercent).pow(period.toDouble())
        return compoundInterest - investment
    }

    fun calculateSimpleInterest(): Double {
        val decimalPercent = percent / 100.0
        return investment * decimalPercent * period
    }


}