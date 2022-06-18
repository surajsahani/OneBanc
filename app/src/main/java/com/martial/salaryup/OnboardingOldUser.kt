package com.martial.salaryup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


/**
 * @Author: surasahani
 * @Date: 16.06.2022
 */

class OnboardingOldUser : AppCompatActivity() {

    private lateinit var getStartedAgain: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old_user)
        initialize()
        onClick()
    }

    fun initialize() {
        getStartedAgain = findViewById(R.id.getStartedAgain);
    }

    fun onClick() {
        getStartedAgain.setOnClickListener {
            //overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            val intent = Intent(this, OnboardingInstructions::class.java)
            startActivity(intent)
        }
    }
}