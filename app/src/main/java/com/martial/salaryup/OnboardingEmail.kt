package com.martial.salaryup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

/**
 * @Author: surasahani
 * @Date: 16.06.2022
 */

class OnboardingEmail : AppCompatActivity() {

    private lateinit var  btNext : Button
    private lateinit var closeIconEmail : ImageView
    private lateinit var etEmail : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_email)
        initialize()
        onClick()
    }

    private fun initialize() {
        btNext = findViewById(R.id.btNext)
        closeIconEmail = findViewById(R.id.closeIconEmail)
        etEmail = findViewById(R.id.etEmail)
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
        inputMode()
    }

    fun inputMode() {
        etEmail.requestFocus()
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etEmail, InputMethodManager.SHOW_IMPLICIT)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}