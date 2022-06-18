package com.martial.salaryup

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            val name: String = etName.text.toString()
            if (name.isNotEmpty()) {
                val intent = Intent(this, OnboardingMobile::class.java)
                startActivity(intent)
            } else {
                etName.error = "Name is required"
            }
        }
        closeIconName.setOnClickListener {
            val intent = Intent(this@OnboardingName, OnboardingInviteCode::class.java)
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

    override fun onStart() {
        super.onStart()
        Log.d(ContentValues.TAG, "Main_OnStart_Name")
    }

    override fun onResume() {
        super.onResume()
        Log.d(ContentValues.TAG, "Main_OnResume_Name")
        etName.setText("")
    }

    override fun onPause() {
        super.onPause()
        Log.d(ContentValues.TAG, "Main_OnPause_Name")
    }

    override fun onStop() {
        super.onStop()
        Log.d(ContentValues.TAG, "Main_OnStop_Name")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(ContentValues.TAG, "Main_OnDestroy_Name")
    }
}