package com.martial.salaryup

import android.content.Context
import android.content.res.Resources
import android.view.View

/**
 * @Author: surajsahani
 * @Date: 19/06/22
 */
/**
 * px to dp
 */
val Int.dp: Int
    get() = this.toFloat().dp.toInt()

val Long.dp: Long
    get() = this.toFloat().dp.toLong()

val Double.dp: Double
    get() = this.toFloat().dp.toDouble()

val Float.dp: Float
    get() = Resources.getSystem().displayMetrics.density * this + 0.5f

/**
 * px to sp
 */
val Int.sp: Int
    get() = this.toFloat().sp.toInt()

val Long.sp: Long
    get() = this.toFloat().sp.toLong()

val Double.sp: Double
    get() = this.toFloat().sp.toDouble()

val Float.sp: Float
    get() = this / Resources.getSystem().displayMetrics.scaledDensity + 0.5f

/**
 *
 */
fun Resources.dp2px(value: Float): Float {
    return this.displayMetrics.density * value + 0.5f
}

/**
 *
 */
fun Context.dp2px(value: Float) = this.resources.dp2px(value)

/**
 *
 */
fun View.dp2px(value: Float) = this.resources.dp2px(value)