package com.latoin.ve.stlator.registration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.latoin.ve.stlator.MenuActivity
import com.latoin.ve.stlator.data.Pref
import com.latoin.ve.stlator.databinding.ActivityMainBinding
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
class BetaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: Pref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = Pref(this)
        initClickers()

        if (sharedPreferences.isAuthSeen()) {
            Intent(this, MenuActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    private fun initClickers() {
        binding.btnRegister.setOnClickListener {
            Intent(this, RegistrationNameActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.btnAnonymous.setOnClickListener {
            Intent(this, MenuActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}