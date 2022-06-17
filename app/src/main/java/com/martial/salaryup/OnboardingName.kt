package com.martial.salaryup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

/**
 * @Author: surasahani
 * @Date: 16.06.2022
 */
class OnboardingName : AppCompatActivity() {

    private lateinit var btNameNext: Button
    private lateinit var closeIconName: ImageView
    private lateinit var etName: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_name)
        initialize()
        onClick()

    }

    fun initialize() {
        closeIconName = findViewById(R.id.closeIconName)
        btNameNext = findViewById(R.id.btNameNext)
        etName = findViewById(R.id.etName)
    }

    fun onClick() {
        btNameNext.setOnClickListener {
            val intent = Intent(this, OnboardingPhone::class.java)
            startActivity(intent)
        }
        closeIconName.setOnClickListener {
            val intent = Intent(this@OnboardingName, OnboardingOldUser::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        inputMode()
    }

    fun inputMode() {
        etName.requestFocus()
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etName, InputMethodManager.SHOW_IMPLICIT)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}