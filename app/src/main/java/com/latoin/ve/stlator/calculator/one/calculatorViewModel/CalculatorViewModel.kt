import androidx.lifecycle.ViewModel
import com.latoin.ve.stlator.R

class CalculatorViewModel : ViewModel() {

    private var investment = 0.0
    private var period = 0
    private var selectedButtonId = 0

    fun setInvestment(value: Double) {
        investment = value
    }

    fun setPeriod(value: Int) {
        period = value
    }

    fun setSelectedButtonId(id: Int) {
        selectedButtonId = id
    }

    fun calculatePeriodInDays(): Int {
        return period
    }

    fun calculateInvestmentResult(): Double {
        return investment
    }
}
