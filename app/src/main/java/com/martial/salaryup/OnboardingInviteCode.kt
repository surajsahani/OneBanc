package com.martial.salaryup

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

/**
 * @Author: surasahani
 * @Date: 16.06.2022
 */
class OnboardingInviteCode : AppCompatActivity() {

    private lateinit var btNextInviteCode: Button
    private lateinit var closeIconInvite: ImageView
    private lateinit var etInviteCode: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "Main_OnCreate")
        setContentView(R.layout.activity_onboarding_invite_code)
        initialize()
        onClick()
    }

    fun initialize() {
        btNextInviteCode = findViewById(R.id.btNextInviteCode)
        closeIconInvite = findViewById(R.id.closeIconInvite)
        etInviteCode = findViewById(R.id.etInviteCode)
    }

    fun onClick() {
        btNextInviteCode.setOnClickListener {

            val code: String = etInviteCode.text.toString()
            if(code.length == 6) {
                val intent = Intent(this, OnboardingName::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }  else  {
                etInviteCode.error = "Invalid Code"
            }

        }

        closeIconInvite.setOnClickListener {
            val intent = Intent(this@OnboardingInviteCode, OnboardingScanCode::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

    }

    fun inputMode() {
        etInviteCode.requestFocus()
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etInviteCode, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Main_OnStart")
        inputMode()
    }


    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Main_OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Main_OnStop")
        etInviteCode.setText("")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Main_OnDestroy")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}