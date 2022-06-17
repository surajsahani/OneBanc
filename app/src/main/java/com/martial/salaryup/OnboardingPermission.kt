package com.martial.salaryup

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * @Author: surasahani
 * @Date: 16.06.2022
 */

class OnboardingPermission : AppCompatActivity() {

    private val TAG = "PermissionDemo"
    private val RECORD_REQUEST_CODE = 101

    private lateinit var smsRead: ImageView
    private lateinit var camera: ImageView
    private lateinit var audio: ImageView
    private lateinit var location: ImageView
    private lateinit var phone: ImageView
    private lateinit var tvSub1: TextView
    private lateinit var tv1Permission: TextView
    private lateinit var grantPermission: Button
    private lateinit var permissionRecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_permission)
        initialize()
        onClick()

        //data to populate the data for recyclerView
        //data to populate the data for recyclerView
        val data: ArrayList<String> = ArrayList()
        for (i in 0..49) {
            data.add("animal # $i")
        }

        permissionRecyclerView = findViewById(R.id.permissionRecyclerView)
        permissionRecyclerView.layoutManager = LinearLayoutManager(this)
        permissionRecyclerView.adapter = OnboardingPermissionAdapter(data)
        permissionRecyclerView.smoothScrollToPosition(0)

    }

    fun initialize() {
        grantPermission = findViewById(R.id.Bt_grantPermission)

    }

    fun onClick() {
        grantPermission.setOnClickListener {
            setupPermission()
        }
    }

    private fun setupPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                Manifest.permission.CAMERA
            )
        ) {
            showRationaleDialog(
                "OneBanc requires camera access",
                "Camera cannot be used as access is denied."
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                Manifest.permission.READ_SMS
            )
        ) {

            showRationaleDialog(
                "OneBanc requires SMS access",
                "SMS cannot be used as access is denied."
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                Manifest.permission.RECORD_AUDIO
            )
        ) {

            showRationaleDialog(
                "OneBanc requires Audio access",
                "Audio cannot be used as access is denied."
            )

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) && shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {

            showRationaleDialog(
                "OneBanc requires Location access",
                "Location cannot be used as access is denied."
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                Manifest.permission.READ_PHONE_STATE
            )
        ) {

            showRationaleDialog(
                "OneBanc requires Phone access",
                "Phone access cannot be used as access is denied."
            )
        } else {
            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_SMS,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE
                )
            )
        }
    }

    private fun showRationaleDialog(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private val permissionResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            Log.d("OnboardingPermission", "Permissions $permissions")
            permissions.entries.forEach {
                val permissionName = it.key
                //if it is granted then we show its granted
                val isGranted = it.value
                if (isGranted) {
//
//                    tvSub1.visibility = INVISIBLE;

                    /*removing static setImage with animation*/

//                    smsRead.setImageResource(R.drawable.ic_tick)
//                    camera.setImageResource(R.drawable.ic_tick)
//                    audio.setImageResource(R.drawable.ic_tick)
//                    location.setImageResource(R.drawable.ic_tick)
//                    phone.setImageResource(R.drawable.ic_tick)

                    val intent = Intent(this, OnboardingScanCode::class.java)
                    startActivity(intent)

                    //check the permission name and perform the specific operation

                    if (permissionName == Manifest.permission.READ_SMS) {
                        Toast.makeText(this, "Permission granted for SMS", Toast.LENGTH_SHORT)
                            .show()
                    }

                    if (permissionName == Manifest.permission.CAMERA) {
                        Toast.makeText(this, "Permission granted for Camera", Toast.LENGTH_SHORT)
                            .show()
                    }

                    if (permissionName == Manifest.permission.RECORD_AUDIO) {
                        Toast.makeText(this, "Permission granted for Audio", Toast.LENGTH_SHORT)
                            .show()
                    }

                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION || permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        Toast.makeText(this, "Permission granted for location", Toast.LENGTH_SHORT)
                            .show()

//                        tv1Permission.setTypeface(Typeface.DEFAULT)
//                        tv1Permission.textSize = 14f
                    }

                    if (permissionName == Manifest.permission.READ_PHONE_STATE) {
                        Toast.makeText(this, "Permission granted for audio", Toast.LENGTH_SHORT)
                            .show()
                    }

                } else {
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION || permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        Toast.makeText(this, "Permission denied location", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Permission denied for Camera", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user")


                } else {
                    Log.i(TAG, "Permission has been granted by user")


                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

