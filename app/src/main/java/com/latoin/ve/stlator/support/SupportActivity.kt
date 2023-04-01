package com.latoin.ve.stlator.support

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.latoin.ve.stlator.databinding.ActivitySupportBinding

class SupportActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySupportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}