package com.latoin.ve.stlator.calculator.result


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.latoin.ve.stlator.databinding.ActivityResultCalculatorBinding

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
            Intent(this, MenuActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun getData() {
        viewModel.investment = intent.getDoubleExtra("invest", 0.0)
        viewModel.period = intent.getIntExtra("period", 0)
        viewModel.result = intent.getDoubleExtra("result", 0.0)
        viewModel.percentType = intent.getStringExtra("percent_type").toString()
    }

    private fun initGraph() {
        val entries = mutableListOf<Entry>()
        var balance = viewModel.investment.toFloat()
        entries.add(Entry(0f, balance))

        if (viewModel.percentType == "comp") {
            for (i in 1..viewModel.period) {
                balance += (balance * viewModel.result / 100).toFloat()
                entries.add(Entry(i.toFloat(), balance))
            }

            lineDataSet = LineDataSet(entries, "Balance over time")
            lineDataSet.color = ContextCompat.getColor(this, R.color.blue)
            lineDataSet.setDrawFilled(true)
            lineDataSet.fillDrawable = ContextCompat.getDrawable(this, R.drawable.graph)
            lineDataSet.fillAlpha = 100
            lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            lineDataSet.lineWidth = 1f
            lineDataSet.setDrawCircles(true)
            lineDataSet.valueTextColor = ContextCompat.getColor(this, R.color.white)

        } else {
            val growthPerMonth = viewModel.result / (viewModel.period.toFloat() * 30)
            for (i in 1..(viewModel.period * 12)) {
                balance += (balance * growthPerMonth / 100).toFloat()
                entries.add(Entry(i.toFloat(), balance))
            }

            lineDataSet = LineDataSet(entries, "Balance over time")
            lineDataSet.color = ContextCompat.getColor(this, R.color.blue)
            lineDataSet.setDrawFilled(true)
            lineDataSet.fillDrawable = ContextCompat.getDrawable(this, R.drawable.graph)
            lineDataSet.fillAlpha = 100
            lineDataSet.mode = LineDataSet.Mode.LINEAR
            lineDataSet.lineWidth = 1f
            lineDataSet.setDrawCircles(false)
            lineDataSet.valueTextColor = ContextCompat.getColor(this, R.color.white)

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

        binding.chart.xAxis.textColor = ContextCompat.getColor(this,R.color.white)
        binding.chart.axisLeft.textColor = ContextCompat.getColor(this,R.color.white)
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

        if (viewModel.percentType == "comp") {
            binding.tvProfit.text =
                "%.2f".format(((balance / viewModel.investment.toFloat()).toDouble() - 1) * 100)
            binding.tvProfitCU.text = "%.2f".format(balance - viewModel.investment.toFloat())
        } else {
            binding.tvProfit.text = "%.2f".format(viewModel.result / viewModel.investment * 100)
            binding.tvProfitCU.text = "%.2f".format(viewModel.result)
        }
        binding.tvInvestment.text = viewModel.investment.toInt().toString()
        binding.tvCurrent.text = "%.2f".format(balance)
    }
}