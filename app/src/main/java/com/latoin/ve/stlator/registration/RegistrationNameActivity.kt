package com.latoin.ve.stlator.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.latoin.ve.stlator.MenuActivity
import com.latoin.ve.stlator.R
import com.latoin.ve.stlator.databinding.ActivityRegistrationNameBinding
import com.latoin.ve.stlator.ext.showToast

class RegistrationNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickers()

    }

    private fun initClickers() {
        binding.btnNext.setOnClickListener {
            if (binding.edName.text.toString().isNotEmpty()) {
                Intent(this, RegistrationPhoneActivity::class.java).apply {
                    startActivity(this)
                }
            } else {
                showToast(getString(R.string.enter_your_name))
            }
        }
        binding.btnAnonymous.setOnClickListener {
            Intent(this, MenuActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}