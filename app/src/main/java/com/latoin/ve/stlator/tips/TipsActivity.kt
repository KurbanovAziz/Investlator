package com.latoin.ve.stlator.tips

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.latoin.ve.stlator.databinding.ActivityTipsBinding

class TipsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityTipsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}