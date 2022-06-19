package com.martial.salaryup

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * @Author: surajsahani
 * @Date: 19/06/22
 */
open class DashLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val HORIZONTAL = 0
        private const val VERTICAL = 1
    }

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRectF = RectF()


    var dashColor = Color.BLACK
        set(value) {
            invalidate()
            field = value
        }


    var dashRadius = 0f
        set(value) {
            invalidate()
            field = value
        }


    var dashGap = dp2px(2f)
        set(value) {
            invalidate()
            field = value
        }


    var dashSize = dp2px(10f)
        set(value) {
            invalidate()
            field = value
        }


    private var orientation = HORIZONTAL

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.DashLineView)
            dashColor = ta.getColor(R.styleable.DashLineView_dlv_dashColor, Color.BLACK)
            dashRadius = ta.getDimension(R.styleable.DashLineView_dlv_dashRadius, dashRadius)
            dashGap = ta.getDimension(R.styleable.DashLineView_dlv_dashGap, dashGap)
            dashSize = ta.getDimension(R.styleable.DashLineView_dlv_dashSize, dashSize)
//            orientation = ta.getInt(R.styleable.DashLineView_dlv_orientation, orientation)
            ta.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.color = dashColor

        orientation  = if (width > height) HORIZONTAL else VERTICAL
        val size = if (orientation == HORIZONTAL) width else height
        val dashLineNum = (size / (dashSize + dashGap)).toInt()

        var left = 0f
        var top = 0f
        var right = 0f
        var bottom = 0f

        for (n in 0..dashLineNum) {
            if (orientation == HORIZONTAL) {
                left = n * (dashSize + dashGap)
                right = left + dashSize
                top = 0f
                bottom = top + height
            } else {
                left = 0f
                right = left + width.toFloat()
                top = n * (dashSize + dashGap)
                bottom = top + dashSize
            }

            mRectF.set(left, top, right, bottom)
            canvas.drawRoundRect(mRectF, dashRadius, dashRadius, mPaint)
        }
    }
}