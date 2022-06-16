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
class OnboardingName : AppCompatActivity() {

    private lateinit var btNameNext : Button
    private lateinit var closeIconName : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_name)

        closeIconName = findViewById(R.id.closeIconName)
        btNameNext = findViewById(R.id.btNameNext)
        btNameNext.setOnClickListener {
            val intent = Intent(this, OnboardingPhone::class.java)
            startActivity(intent)
        }

        closeIconName.setOnClickListener {

            val intent = Intent(this@OnboardingName, OnboardingInviteCode::class.java)
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