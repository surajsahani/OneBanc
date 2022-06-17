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
class OnboardingInviteCode : AppCompatActivity() {

    private lateinit var btNextInviteCode : Button
    private lateinit var closeIconInvite : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_invite_code)
        initialize()
        onClick()
    }

    fun initialize() {
        btNextInviteCode = findViewById(R.id.btNextInviteCode)
        closeIconInvite = findViewById(R.id.closeIconInvite)
    }
    fun onClick() {
        btNextInviteCode.setOnClickListener {
            //overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            val intent = Intent(this, OnboardingName::class.java)
            startActivity(intent)
        }

        closeIconInvite.setOnClickListener {
            val intent = Intent(this@OnboardingInviteCode, OnboardingScanCode::class.java)
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