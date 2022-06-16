package com.martial.salaryup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class OnboardingInstructions : AppCompatActivity() {

    private lateinit var closeIconPermission: ImageView
    private lateinit var  button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_onboarding)

        initialize()
        onClick()
    }

    fun initialize() {
        closeIconPermission = findViewById(R.id.closeIconPermission);
        button = findViewById(R.id.button)
    }

    fun onClick() {

        closeIconPermission.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_stay, R.anim.slide_out_up)
//            val intent = Intent(this, OldUser::class.java)
//            startActivity(intent)
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