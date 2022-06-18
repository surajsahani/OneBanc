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

class OnboardingInstructions : AppCompatActivity() {

    private lateinit var closeIconInstruction: ImageView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_instructions)
        initialize()
        onClick()
    }

    fun initialize() {
        closeIconInstruction = findViewById(R.id.closeIconInstruction);
        button = findViewById(R.id.button)
    }

    fun onClick() {

        closeIconInstruction.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_stay, R.anim.slide_out_up)
        }

        button.setOnClickListener {
            val intent = Intent(this, OnboardingPermission::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_stay, R.anim.slide_out_up)
    }
}