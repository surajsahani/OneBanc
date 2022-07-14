package com.martial.salaryup


import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    RecyclerView.Adapter<OnboardingPermissionAdapter.OnboardingPermissionHolder>(){

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
    var mcontext: Context? = null

    inner class OnboardingPermissionHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv: TextView? = null
        var subTv: TextView? = null
        var iconsIv: ImageView? = null
        var dlvHeader: View? = null
        var dlvHeaderTwo: View? = null
        var dlvHeaderThree: View? = null
        var dlvFooter: View? = null
        var dlvFooterTwo: View? = null
        var dlvFooterThree: View? = null


        init {
            tv = view.findViewById(R.id.tv1Permission)
            subTv = view.findViewById(R.id.tvSub1)
            iconsIv = view.findViewById(R.id.ivIcon)
            dlvHeader = view.findViewById(R.id.dlv_header)
            dlvHeaderTwo = view.findViewById(R.id.dlv_headerTwo)
            dlvHeaderThree = view.findViewById(R.id.dlv_headerThree)
            dlvFooter = view.findViewById(R.id.dlv_footer)
            dlvFooterTwo = view.findViewById(R.id.dlv_footerTwo)
            dlvFooterThree = view.findViewById(R.id.dlv_footerThree)

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


        if (position == 0) {
            holder.dlvHeader?.visibility = View.GONE
            holder.dlvHeaderTwo?.visibility = View.GONE
            holder.dlvHeaderThree?.visibility = View.GONE


            holder.dlvFooter?.visibility = View.VISIBLE
            holder.dlvFooterTwo?.visibility = View.GONE
            holder.dlvFooterThree?.visibility = View.GONE

            holder.tv?.apply {
                textSize = 18f
            }
        }
        if (position == 1) {
            holder.dlvHeader?.visibility = View.VISIBLE
            holder.dlvHeaderTwo?.visibility = View.GONE
            holder.dlvHeaderThree?.visibility = View.GONE

            holder.dlvFooter?.visibility = View.GONE
            holder.dlvFooterTwo?.visibility = View.GONE
            holder.dlvFooterThree?.visibility = View.GONE

            holder.tv?.apply {
                textSize = 14f
            }
            holder.subTv?.apply {
                textSize = 0f
            }
        }
        if (position == 2) {
            holder.dlvHeader?.visibility = View.VISIBLE
            holder.dlvHeaderTwo?.visibility = View.GONE
            holder.dlvHeaderThree?.visibility = View.GONE


            holder.dlvFooter?.visibility = View.GONE
            holder.dlvFooterTwo?.visibility = View.GONE
            holder.dlvFooterThree?.visibility = View.GONE

            holder.tv?.apply {
                textSize = 14f
            }
            holder.subTv?.apply {
                textSize = 0f
            }
        }
        if (position == 3) {
            holder.dlvHeader?.visibility = View.VISIBLE
            holder.dlvHeaderTwo?.visibility = View.GONE
            holder.dlvHeaderThree?.visibility = View.GONE

            holder.dlvFooter?.visibility = View.GONE
            holder.dlvFooterTwo?.visibility = View.GONE
            holder.dlvFooterThree?.visibility = View.GONE

            holder.tv?.apply {
                textSize = 14f
            }
            holder.subTv?.apply {
                textSize = 0f
            }
        }
        if (position == 4) {
            holder.dlvHeader?.visibility = View.VISIBLE
            holder.dlvHeaderTwo?.visibility = View.GONE
            holder.dlvHeaderThree?.visibility = View.GONE

            holder.dlvFooter?.visibility = View.GONE
            holder.dlvFooterTwo?.visibility = View.GONE
            holder.dlvFooterThree?.visibility = View.GONE

            holder.tv?.apply {
                textSize = 14f
            }
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
            } else {
                this.isSelected = false
            }
        }
        holder.subTv?.apply {
            this.text = subText
            this.setOnClickListener {
                refresh(position)
            }
            if (position == checkItem) {
                this.isSelected = true
            } else {
                this.isSelected = false
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

    interface OnClickListener {
        fun permissionData(item: String?)
    }
}