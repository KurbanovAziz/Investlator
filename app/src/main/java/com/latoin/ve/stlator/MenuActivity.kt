package com.latoin.ve.stlator

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.latoin.ve.stlator.calculator.one.CalculatorActivity
import com.latoin.ve.stlator.databinding.ActivityMenuBinding
import com.latoin.ve.stlator.support.SupportActivity
import com.latoin.ve.stlator.tips.TipsActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.calculatorLayout.setOnClickListener {
            val calculatorIntent = Intent(this, CalculatorActivity::class.java)
            startActivity(calculatorIntent)
        }
        binding.tipsLayout.setOnClickListener {
            val tipsIntent = Intent(this, TipsActivity::class.java)
            startActivity(tipsIntent)
        }
        binding.supportLayout.setOnClickListener {
            val supportIntent = Intent(this, SupportActivity::class.java)
            startActivity(supportIntent)
        }
        binding.btnLogout.setOnClickListener {
            finish()
        }
        binding.btnPrivacy.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"))
            startActivity(browserIntent)
        }
    }
}