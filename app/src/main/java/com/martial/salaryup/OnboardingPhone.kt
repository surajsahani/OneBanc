package com.martial.salaryup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

/**
 * @Author: surasahani
 * @Date: 16.06.2022
 */

class OnboardingPhone : AppCompatActivity() {

    private lateinit var bt_PhoneNext: Button
    private lateinit var closeIconPhone : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_phone)
        initialize()
        onClick()
    }

    private fun initialize() {
        bt_PhoneNext = findViewById(R.id.bt_PhoneNext)
        closeIconPhone = findViewById(R.id.closeIconPhone)
    }

    private fun onClick() {
        bt_PhoneNext.setOnClickListener {
            val intent = Intent(this, OnboardingEmail::class.java)
            startActivity(intent)
        }

        closeIconPhone.setOnClickListener {
            val intent = Intent(this@OnboardingPhone, OnboardingName::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}