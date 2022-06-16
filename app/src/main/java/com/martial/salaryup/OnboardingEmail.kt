package com.martial.salaryup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class OnboardingEmail : AppCompatActivity() {

    private lateinit var  btNext : Button
    private lateinit var closeIconEmail : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_email)
        initialize()
        onClick()
    }

    private fun initialize() {
        btNext = findViewById(R.id.btNext)
        closeIconEmail = findViewById(R.id.closeIconEmail)

    }

    private fun onClick() {
        btNext.setOnClickListener {
            val intent = Intent(this, OnboardingCongratulation::class.java)
            startActivity(intent)
        }
        closeIconEmail.setOnClickListener {
            val intent = Intent(this@OnboardingEmail, OnboardingName::class.java)
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