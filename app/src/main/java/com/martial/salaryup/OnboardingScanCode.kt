package com.martial.salaryup


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector


/**
 * @Author: surasahani
 * @Date: 16.06.2022
 */

class OnboardingScanCode : AppCompatActivity() {

    private lateinit var scannerView: SurfaceView
    private lateinit var tvBarcode: TextView
    private lateinit var detector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
    private lateinit var surfaceBlur: SurfaceView
    private lateinit var backIconScan: ImageView
    private lateinit var scannerFlash: ImageView
    private lateinit var scannerGallary: ImageView
    private lateinit var animeteOR: View
    private var cameraManager: CameraManager? = null
    private lateinit var mContext: Context

    val CAMERA_FRONT = "1"
    val CAMERA_BACK = "0"

    private val cameraId = CAMERA_BACK
    private val isFlashSupported = false
    private val isTorchOn = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_scan)

        initialize()
        motion()
        cameraSource()
        onClick()
    }

    fun initialize() {
        scannerView = findViewById(R.id.scannerView)
        tvBarcode = findViewById(R.id.tvBarcode)
        surfaceBlur = findViewById(R.id.surfaceBlur)
        backIconScan = findViewById(R.id.backIconScan)
        scannerFlash = findViewById(R.id.scannerFlash)
        scannerGallary = findViewById(R.id.scannerGallary)
        animeteOR = findViewById(R.id.animeteOR)
    }

    fun motion() {
        val animation = TranslateAnimation(0f, 0f, -200f, 200f)
        animation.duration = 1000;
        animation.fillAfter = true;
        animation.isFillEnabled = true;
        animation.repeatMode = Animation.REVERSE
        animation.repeatCount = Animation.INFINITE
        animeteOR.startAnimation(animation)
    }

    fun cameraSource() {
        detector = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()
        detector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                var barcodes = detections?.detectedItems
                if (barcodes!!.size() > 0) {
                    val builder = StringBuilder()
                    tvBarcode.post {
                        builder.append("Invite Code : ")
                        builder.append("\n")
                        builder.append(barcodes.valueAt(0).displayValue)
                        tvBarcode.text = builder.toString()
                        //tvBarcode.text = barcodes.valueAt(0).displayValue
                    }
                }
            }
        })

        cameraSource = CameraSource.Builder(this, detector).setRequestedPreviewSize(1024, 768)
            .setRequestedFps(25f).setAutoFocusEnabled(true).build()


        scannerView.holder.addCallback(object : SurfaceHolder.Callback2 {
            override fun surfaceRedrawNeeded(p0: SurfaceHolder) {

            }

            override fun surfaceChanged(p0: SurfaceHolder, format: Int, w: Int, h: Int) {}

            // Main Camera processing.
            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()
            }

            override fun surfaceCreated(p0: SurfaceHolder) {
                // Check Camera permission
                if (ContextCompat.checkSelfPermission(
                        this@OnboardingScanCode,
                        android.Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    cameraSource.start(p0)
                } else {
                    ActivityCompat.requestPermissions(
                        this@OnboardingScanCode,
                        arrayOf(android.Manifest.permission.CAMERA),
                        123
                    )
                }

            }
        })

        // surfaceBlur.setBackgroundColor(0Xffffffff.toInt())
//        surfaceBlur.holder.setFormat(PixelFormat.TRANSLUCENT);

        surfaceBlur.holder.addCallback(object : SurfaceHolder.Callback2 {
            override fun surfaceRedrawNeeded(p0: SurfaceHolder) {

            }

            override fun surfaceChanged(p0: SurfaceHolder, format: Int, w: Int, h: Int) {

            }

            // Main Camera processing.
            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()
            }

            override fun surfaceCreated(p0: SurfaceHolder) {
                // Check Camera permission

                if (ContextCompat.checkSelfPermission(
                        this@OnboardingScanCode,
                        android.Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    cameraSource.start(p0)

                } else {
                    ActivityCompat.requestPermissions(
                        this@OnboardingScanCode,
                        arrayOf(android.Manifest.permission.CAMERA),
                        123
                    )
                }

            }
        })
    }

    fun onClick() {
        tvBarcode.setOnClickListener {
            val intent = Intent(this, OnboardingInviteCode::class.java)
            startActivity(intent)
        }

        backIconScan.setOnClickListener {

            val intent = Intent(this@OnboardingScanCode, OnboardingPermission::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 123) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraSource.start(scannerView.holder)
            } else {
                Toast.makeText(this, "Scanner is available to allow Camera.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detector.release()
        cameraSource.stop()
        cameraSource.release()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }




    fun flashLightOn() {
        try {
            val cameraId = cameraManager!!.cameraIdList[0]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager!!.setTorchMode(cameraId, true)
            }
        } catch (e: CameraAccessException) {
        }
    }

}
