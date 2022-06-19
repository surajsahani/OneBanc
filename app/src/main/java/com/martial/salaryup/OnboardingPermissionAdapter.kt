package com.martial.salaryup


import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView


/**
 * @Author: surajsahani
 * @Date: 16/06/22
 */


open class OnboardingPermissionAdapter(
    private val context: Context,
    private var data: ArrayList<String>,
    private val itemclick: OnClickListener
) :
    RecyclerView.Adapter<OnboardingPermissionAdapter.OnboardingPermissionHolder>(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    private var listSize = listOf("SMS Read", "Camera", "Microphone", "Location", "Phone Identity")
    private val liseSizeSub = listOf(
        "We need this access to verify your SIM card",
        "We need this access for seamless video KYC process",
        "We need this access for seamless video KYC process",
        "We need this to verify communication address during video KYC process",
        "We need this access to verify your mobile number"
    )
    private val imageList = listOf(
        R.drawable.ic_sms,
        R.drawable.ic_camera,
        R.drawable.ic_mic,
        R.drawable.ic_location,
        R.drawable.ic_phone
    )

    private var checkItem = -1
    private val RECORD_REQUEST_CODE = 101
    var mcontext: Context? = null

    inner class OnboardingPermissionHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv: TextView? = null
        var subTv: TextView? = null
        var iconsIv: ImageView? = null
        var dlvHeader : View? = null
        var dlvFooter : View? = null
        init {
            tv = view.findViewById(R.id.tv1Permission)
            subTv = view.findViewById(R.id.tvSub1)
            iconsIv = view.findViewById(R.id.ivIcon)
            dlvHeader = view.findViewById(R.id.dlv_header)
            dlvFooter = view.findViewById(R.id.dlv_footer)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingPermissionHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
        return OnboardingPermissionHolder(view)


    }

    override fun onBindViewHolder(holder: OnboardingPermissionHolder, position: Int) {
        val text = listSize[position]
        val subText = liseSizeSub[position]
        val icons = imageList[position]


        holder.dlvHeader?.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
        holder.dlvHeader?.visibility = if (position == 5) View.INVISIBLE else View.VISIBLE


        if (position == 0) {
//            holder.dlvHeader?.visibility = View.INVISIBLE
            holder.tv?.apply {
                textSize = 40f

            }
        }
        if (position == 1) {
            holder.tv?.apply {

            }
            holder.subTv?.apply {
                textSize = 0f
            }
        }
        if (position == 2) {
            holder.tv?.apply {

            }
            holder.subTv?.apply {
                textSize = 0f
            }
        }
        if (position == 3) {
            holder.subTv?.apply {
                textSize = 0f
            }
        }
        if (position == 4) {
//            holder.dlvFooter?.visibility = View.INVISIBLE
            holder.subTv?.apply {
                textSize = 0f
            }
        }
//        if (position % 5 == 0) {
//            holder.tv?.textSize = 30f
//            holder.tv.
//        } else {
//            holder.subTv?.textSize = 0f
//        }
        holder.tv?.setOnClickListener {
            Toast.makeText(mcontext, "Fuck handler", Toast.LENGTH_SHORT).show()
            holder.tv?.textSize = 0f
        }

        holder.tv?.apply {
            this.text = text
            this.setOnClickListener {
                refresh(position)
            }
            if (position == checkItem) {
                this.isSelected = true
                this.setTextColor(R.color.white)
            } else {
                this.isSelected = false
                this.setTextColor(R.color.black)
            }
        }
        holder.subTv?.apply {
            this.text = subText
            this.setOnClickListener {
                refresh(position)
            }
            if (position == checkItem) {
                this.isSelected = true
                this.setTextColor(R.color.white)
            } else {
                this.isSelected = false
                this.setTextColor(R.color.black)
            }
        }

        holder.iconsIv?.apply {
            //this.text = icons
            this.setImageResource(icons)
            this.setOnClickListener {
                refresh(position)
            }
            this.isSelected = position == checkItem
        }
        if (holder.tv?.id == 1) {
            holder.tv?.textSize = 90f
        }

    }


    private fun refresh(position: Int) {
        checkItem = position
        notifyDataSetChanged()
    }

    fun getCheckedSize() = listSize[checkItem]

    private fun onItemClick(position: Int) {
        Toast.makeText(mcontext, listSize[position], Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount() = listSize.size

    companion object {
        val TAG = "ModalAdapter"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        this.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

    interface OnClickListener {
        fun permissionData(item: String?)
    }
}