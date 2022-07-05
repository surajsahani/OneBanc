package com.martial.salaryup


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.security.AccessController.getContext


/**
 * @Author: surasahani
 * @Date: 16.06.2022
 */

class OnboardingScanCode : AppCompatActivity() {

    private lateinit var svBarcode: SurfaceView
    private lateinit var tvBarcode: TextView
    private lateinit var detector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
    private lateinit var scannerFlash: ImageView
    private lateinit var surfaceBlur: SurfaceView
    private lateinit var closeIconScan: ImageView
    private lateinit var scannerGallary: ImageView
    private lateinit var animeteOR: View

    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_scan)


        svBarcode = findViewById(R.id.scannerView)

        tvBarcode = findViewById(R.id.etbarCode)

        surfaceBlur = findViewById(R.id.surfaceBlur)

        scannerFlash = findViewById(R.id.scannerFlash)

        closeIconScan = findViewById(R.id.closeIconScan)

        scannerGallary = findViewById(R.id.scannerGallary)

        animeteOR = findViewById(R.id.animeteOR)


        // animeteOR.animate().x(500f).y(500f).setDuration(10000).start()

        // animeteOR.startAnimation(R.anim.slide_in_up)
        mContext = this

        //animeteOR.animate().x(100f).z(100f).setDuration(1000).start()

        // animeteOR.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.animupdown))

//        val scanningView = findViewById<View>(R.id.animationHorizentalLine) as MyScanningView
//        scanningView.startAnimation() //To start Animation
//
//        animationLine = findViewById(R.id.animationHorizentalLine)
//        val animUpDown: Animation = AnimationUtils.loadAnimation(
//            applicationContext,
//            R.anim.animupdown
//        )
//        animationLine.setOnClickListener {
//            animationLine.startAnimation(animUpDown)
//        }


        val sfvTrack = findViewById<SurfaceView>(R.id.surfaceBlur)
        //sfvTrack.setZOrderOnTop(true) // necessary
        val sfhTrackHolder = sfvTrack.holder
        sfhTrackHolder.setFormat(PixelFormat.TRANSLUCENT)


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



        svBarcode.holder.addCallback(object : SurfaceHolder.Callback2 {
            override fun surfaceRedrawNeeded(p0: SurfaceHolder) {}

            override fun surfaceChanged(p0: SurfaceHolder, format: Int, w: Int, h: Int) {}

            //Main Camera processing.
            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()
            }

            override fun surfaceCreated(p0: SurfaceHolder) {
                //Check Camera permission
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

        //surfaceBlur.setBackgroundColor(0Xffffffff.toInt())
        surfaceBlur.holder.setFormat(PixelFormat.TRANSLUCENT);

        surfaceBlur.holder.addCallback(object : SurfaceHolder.Callback2 {
            override fun surfaceRedrawNeeded(p0: SurfaceHolder) {}

            override fun surfaceChanged(p0: SurfaceHolder, format: Int, w: Int, h: Int) {}

            //Main Camera processing.
            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()

            }

            override fun surfaceCreated(p0: SurfaceHolder) {
                //Check Camera permission

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

        tvBarcode.setOnClickListener {
            val intent = Intent(this, OnboardingInviteCode::class.java)
            startActivity(intent)
        }

        scannerFlash.setOnClickListener {
            handleActionTurnOnFlashLight(this)
        }

        closeIconScan.setOnClickListener {

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
                cameraSource.start(svBarcode.holder)
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

    private fun handleActionTurnOnFlashLight(context: Context) {

    }

    private fun handleActionTurnOffFlashLight(context: Context) {

    }
}
