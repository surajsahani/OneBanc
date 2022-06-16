package com.martial.salaryup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class OnboardingCongratulation : AppCompatActivity() {

    private lateinit var closeIconCongratulation : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_congratulation)

        closeIconCongratulation = findViewById(R.id.closeIconCongratulation)

        closeIconCongratulation.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_stay, R.anim.slide_out_up)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}