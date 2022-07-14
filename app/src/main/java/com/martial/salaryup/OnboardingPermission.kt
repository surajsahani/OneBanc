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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * @Author: surasahani
 * @Date: 16.06.2022
 */

class OnboardingPermission : AppCompatActivity() {

    companion object {
        val REQUEST_PERMISSION_CODE = 10
    }

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
    private lateinit var permissionRecyclerView: RecyclerView

    private lateinit var closeIconPermission: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_permission)
        initialize()
        onClick()

        //data to populate the data for recyclerView
        val data: ArrayList<String> = ArrayList()
        for (i in 0..5) {
            data.add("permission # $i")
        }

        permissionRecyclerView = findViewById(R.id.permissionRecyclerView)
        permissionRecyclerView.layoutManager = LinearLayoutManager(this)
        permissionRecyclerView.adapter = OnboardingPermissionAdapter(

            this, data, object : OnboardingPermissionAdapter.OnClickListener {
                override fun permissionData(item: String?) {
                }
            }
        )

    }

    fun initialize() {
        grantPermission = findViewById(R.id.Bt_grantPermission)
        closeIconPermission = findViewById(R.id.closeIconPermission);

    }

    fun onClick() {
        grantPermission.setOnClickListener {
            setupPermission()
//            smsPermission()
//            cameraPermission()
//            microphonePermission()
//            locationPermission()
//            phoneIdentity()
        }
        closeIconPermission.setOnClickListener {
            finish()
        }
    }


    private fun smsPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        if (checkSelfPermission(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
        } else {
            var permission = arrayOf(Manifest.permission.READ_SMS)
            requestPermissions(permission, REQUEST_PERMISSION_CODE)
        }
    }

    private fun cameraPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        } else {
            var permission = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(permission, REQUEST_PERMISSION_CODE)
        }
    }

    private fun microphonePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
        } else {
            var permission = arrayOf(Manifest.permission.RECORD_AUDIO)
            requestPermissions(permission, REQUEST_PERMISSION_CODE)
        }
    }

    private fun locationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else {
            var permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permission, REQUEST_PERMISSION_CODE)
        }
    }

    private fun phoneIdentity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            var permission = arrayOf(Manifest.permission.READ_PHONE_STATE)
            requestPermissions(permission, REQUEST_PERMISSION_CODE)
        }
    }

    private fun setupPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
        )

            showRationaleDialog(
                "OneBanc requires camera access",
                "Camera cannot be used as access is denied."
            )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                Manifest.permission.READ_SMS
            )
        )
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

                    val intent = Intent(this, OnboardingScanCode::class.java)
                    startActivity(intent)


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

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            RECORD_REQUEST_CODE -> {
//
//                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//
//                    Log.i(TAG, "Permission has been denied by user")
//
//
//                } else {
//                    Log.i(TAG, "Permission has been granted by user")
//
//
//                }
//            }
//        }
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

