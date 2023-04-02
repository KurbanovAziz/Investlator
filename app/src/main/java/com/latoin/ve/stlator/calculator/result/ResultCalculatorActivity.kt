package com.latoin.ve.stlator.calculator.result


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.latoin.ve.stlator.MenuActivity
import com.latoin.ve.stlator.R
import com.latoin.ve.stlator.calculator.two.CalculateTwoActivity.Companion.KEY_COMP
import com.latoin.ve.stlator.calculator.two.CalculateTwoActivity.Companion.KEY_INVEST
import com.latoin.ve.stlator.calculator.two.CalculateTwoActivity.Companion.KEY_PERCENT_TYPE
import com.latoin.ve.stlator.calculator.two.CalculateTwoActivity.Companion.KEY_PERIOD
import com.latoin.ve.stlator.calculator.two.CalculateTwoActivity.Companion.KEY_RESULT
import com.latoin.ve.stlator.databinding.ActivityResultCalculatorBinding

@Suppress("DEPRECATION")
class ResultCalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultCalculatorBinding
    private lateinit var lineDataSet: LineDataSet
    private lateinit var viewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ResultViewModel::class.java]

        getData()
        initGraph()
        initListener()

    }

    private fun initListener() {
        binding.btnMenu.setOnClickListener {
            navigateTo()
        }
    }

    private fun getData() {
        viewModel.investment = intent.getDoubleExtra(KEY_INVEST, 0.0)
        viewModel.period = intent.getIntExtra(KEY_PERIOD, 0)
        viewModel.result = intent.getDoubleExtra(KEY_RESULT, 0.0)
        viewModel.percentType = intent.getStringExtra(KEY_PERCENT_TYPE).toString()
    }

    private fun initGraph() {
        val entries = mutableListOf<Entry>()
        var balance = viewModel.investment.toFloat()
        entries.add(Entry(0f, balance))

        if (viewModel.percentType == KEY_COMP) {
            for (i in 1..viewModel.period) {
                balance += (viewModel.result / 10).toFloat()
                entries.add(Entry(i.toFloat(), balance))
            }
            lineDataSet = LineDataSet(entries, KEY_BALANCE)
            setLineDataSetStyle(lineDataSet, LineDataSet.Mode.CUBIC_BEZIER)

        } else {
            for (i in 1..(viewModel.period)) {
                balance += (viewModel.result / 10).toFloat()
                entries.add(Entry(i.toFloat(), balance))
            }

            lineDataSet = LineDataSet(entries, KEY_BALANCE)
            setLineDataSetStyle(lineDataSet, LineDataSet.Mode.LINEAR)

        }

        val lineData = LineData(lineDataSet)
        binding.chart.data = lineData

        binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chart.axisRight.isEnabled = false

        val maxBalance = entries.maxByOrNull { it.y }?.y ?: balance
        binding.chart.axisLeft.axisMinimum = viewModel.investment.toFloat()
        binding.chart.axisLeft.axisMaximum = maxBalance * 1.1f

        val formatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return if (viewModel.period > 30) {
                    if (value % 3 == 0f) {
                        (value.toInt() + 1).toString()
                    } else {
                        ""
                    }
                } else {
                    (value.toInt() + 1).toString()
                }
            }
        }

        setChartStyle(formatter)

        binding.tvProfit.text = getString(
            R.string.profit_text,
            "%.2f".format(viewModel.result / viewModel.investment * 100)
        )
        binding.tvProfitCU.text =
            getString(R.string.profit_cu_text, "%.2f".format(viewModel.result))
        binding.tvInvestment.text =
            getString(R.string.investment_text, viewModel.investment.toInt().toString())
        binding.tvCurrent.text =
            getString(R.string.current_text, "%.2f".format(viewModel.result + viewModel.investment))

        setMarqueeEffect(binding.tvProfit)
        setMarqueeEffect(binding.tvProfitCU)
        setMarqueeEffect(binding.tvInvestment)
        setMarqueeEffect(binding.tvCurrent)
    }

    private fun setLineDataSetStyle(lineDataSet: LineDataSet, mode: LineDataSet.Mode) {
        lineDataSet.color = ContextCompat.getColor(this, R.color.blue)
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable = ContextCompat.getDrawable(this, R.drawable.graph)
        lineDataSet.fillAlpha = 100
        lineDataSet.mode = mode
        lineDataSet.lineWidth = 1f
        lineDataSet.setDrawCircles(true)
        lineDataSet.valueTextColor = ContextCompat.getColor(this, R.color.white)
    }

    private fun setChartStyle(formatter: ValueFormatter) {
        binding.chart.xAxis.textColor = ContextCompat.getColor(this, R.color.white)
        binding.chart.axisLeft.textColor = ContextCompat.getColor(this, R.color.white)
        binding.chart.xAxis.textSize = 12f
        binding.chart.axisLeft.textSize = 12f
        binding.chart.xAxis.granularity = 1f
        binding.chart.xAxis.valueFormatter = formatter
        binding.chart.description.isEnabled = false
        binding.chart.legend.isEnabled = false
        binding.chart.setTouchEnabled(true)
        binding.chart.isDragEnabled = true
        binding.chart.setScaleEnabled(true)
        binding.chart.setPinchZoom(true)
        binding.chart.axisLeft.isGranularityEnabled = true
        binding.chart.axisLeft.granularity = 1f
        binding.chart.setVisibleXRangeMaximum(10f)
        binding.chart.moveViewToX(0f)
        binding.chart.animateXY(1000, 1000)

        binding.chart.invalidate()
    }

    private fun setMarqueeEffect(textView: TextView) {
        textView.isSelected = true
        textView.ellipsize = TextUtils.TruncateAt.MARQUEE
        textView.marqueeRepeatLimit = -1
    }

    private fun navigateTo() {
        Intent(this, MenuActivity::class.java).apply {
            startActivity(this)
            onBackPressed()
        }
    }

    companion object {
        const val KEY_BALANCE = "Balance over time"
    }

}