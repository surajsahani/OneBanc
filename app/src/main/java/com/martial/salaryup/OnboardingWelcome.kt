package com.martial.salaryup

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


/**
 * @Author: surasahani
 * @Date: 16.06.2022
 */

class OnboardingWelcome : AppCompatActivity() {

    private lateinit var getStarted: Button
    private lateinit var welcomeTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_welcome)
        initialize()
        onClick()
    }

    fun initialize() {
        getStarted = findViewById(R.id.getStarted);
        welcomeTv = findViewById(R.id.welcomeTv)
    }

    fun onClick() {
        getStarted.setOnClickListener {
            //overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            val intent = Intent(this, OnboardingInstructions::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_stay);
        }
    }
}