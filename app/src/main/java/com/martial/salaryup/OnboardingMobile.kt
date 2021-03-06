package com.martial.salaryup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import java.util.regex.Pattern

/**
 * @Author: surasahani
 * @Date: 16.06.2022
 */

class OnboardingMobile : AppCompatActivity() {

    private lateinit var bt_PhoneNext: Button
    private lateinit var closeIconPhone: ImageView
    private lateinit var etPhone: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_mobile)
        initialize()
        onClick()
    }

    private fun initialize() {
        bt_PhoneNext = findViewById(R.id.bt_PhoneNext)
        closeIconPhone = findViewById(R.id.closeIconPhone)
        etPhone = findViewById(R.id.etPhone)
    }

    private fun isValidPhone(email: String): Boolean {
        val pattern: Pattern = Patterns.PHONE
        return pattern.matcher(email).matches()
    }

    private fun onClick() {

        bt_PhoneNext.setOnClickListener {

            val email: String = etPhone.text.toString()
            if (email.isNotEmpty()) {
                if (isValidPhone(email)) {
                    val intent = Intent(this@OnboardingMobile, OnboardingEmail::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    etPhone.error = "Invalid phone!"
                }
            } else {
                etPhone.error = "Phone number required!"
            }

        }
        closeIconPhone.setOnClickListener {
            val intent = Intent(this@OnboardingMobile, OnboardingName::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        inputMode()
    }

    fun inputMode() {
        etPhone.requestFocus()
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etPhone, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onRestart() {
        super.onRestart()
        etPhone.setText("")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}