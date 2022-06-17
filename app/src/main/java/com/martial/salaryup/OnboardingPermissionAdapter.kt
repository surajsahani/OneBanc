package com.martial.salaryup


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


/**
 * @Author: surajsahani
 * @Date: 16/06/22
 */

class OnboardingPermissionAdapter(val mContext: ArrayList<String>) :
    RecyclerView.Adapter<OnboardingPermissionAdapter.OnboardingPermissionHolder>() {
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

    inner class OnboardingPermissionHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv: TextView? = null
        var subTv: TextView? = null
        var cameraIc: ImageView? = null

        init {
            tv = view.findViewById(R.id.tv1Permission)
            subTv = view.findViewById(R.id.tvSub1)
            cameraIc = view.findViewById(R.drawable.ic_camera)
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
        holder.cameraIc?.apply {
            //this.text = icons
            this.setImageResource(icons)
            this.setOnClickListener {
                refresh(position)
            }
            if (position == checkItem) {
                this.isSelected = true
                this.setImageResource(icons)
                //this.setTextColor(R.color.white)
            } else {
                this.isSelected = false
                this.setImageResource(icons)
                //this.setTextColor(R.color.black)
            }
        }
    }

    private fun refresh(position: Int) {
        checkItem = position
        notifyDataSetChanged()
    }

    private fun setTextSizes(textSize: Int) {
        this.listSize = listSize
        notifyDataSetChanged()
    }

    fun getCheckedSize() = listSize[checkItem]

    override fun getItemCount() = listSize.size

    companion object {
        val TAG = "ModalAdapter"
    }
}