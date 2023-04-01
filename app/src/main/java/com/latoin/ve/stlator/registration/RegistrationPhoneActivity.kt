package com.latoin.ve.stlator.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.latoin.ve.stlator.MenuActivity
import com.latoin.ve.stlator.R
import com.latoin.ve.stlator.data.Pref
import com.latoin.ve.stlator.databinding.ActivityRegistrationPhoneBinding
import com.latoin.ve.stlator.ext.showToast
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil

class RegistrationPhoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationPhoneBinding
    private lateinit var sharedPreferences: Pref
    private lateinit var phoneNumberUtil: PhoneNumberUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = Pref(this)
        phoneNumberUtil = PhoneNumberUtil.createInstance(this)

        initClickers()
        if (sharedPreferences.isAuthSeen()){
            goToMenu()
            finish()
        }
    }

    private fun initClickers() {
        binding.btnDone.setOnClickListener {
            authPhone()
        }
        binding.btnAnonymous.setOnClickListener {
            goToMenu()
        }
    }

    private fun authPhone() {
        val phone = binding.edPhone.text.toString()
        val countryCode = binding.ccp.selectedCountryCode
        if (phone.isNotEmpty() && countryCode.isNotEmpty()) {
            val numberProto = try {
                phoneNumberUtil.parse(phone, binding.ccp.selectedCountryNameCode)
            } catch (e: NumberParseException) {
                null
            }
            if (numberProto != null && phoneNumberUtil.isValidNumber(numberProto)) {
                phoneNumberUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164)
                goToMenu()
                sharedPreferences.setAuthSeen(true)
            } else {
                showToast(getString(R.string.check_if_the_number_is_correct))
            }
        } else {
            showToast(getString(R.string.all_fields_must_be_filled))
        }
        binding.ccp.registerCarrierNumberEditText(binding.edPhone)
    }

    private fun goToMenu() {
        Intent(this, MenuActivity::class.java).apply {
            startActivity(this)
        }
    }

}