package com.example.rectpivotrotlineview

import android.view.View
import android.view.MotionEvent
import android.content.Context
import android.app.Activity
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF

val parts : Int = 4
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val sizeFactor : Float = 4.9f
val delay : Long = 20
val colors : Array<Int> = arrayOf(
    "#F44336",
    "#9C27B0",
    "#009688",
    "#FF9800",
    "#2196F3"
).map {
    Color.parseColor(it)
}.toTypedArray()
val backColor : Int = Color.parseColor("#BDBDBD")
val rot : Float = 90f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawRectPivotRotLine(scale : Float, w : Float, h : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    val size : Float = Math.min(w, h) / sizeFactor
    save()
    translate(w / 2, h / 2)
    drawRect(RectF(0f, -size / 10, size * sf1, size / 10), paint)
    drawArc(
        RectF(-size / 3, -size / 3, size / 3, size / 3),
        360f - rot - rot * sf3,
        rot * sf3,
        true,
        paint)
    save()
    rotate(rot * sf3)
    drawLine(0f, 0f, -size * sf2, 0f, paint)
    restore()
    restore()
}

fun Canvas.drawRPRLNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawRectPivotRotLine(scale, w, h, paint)
}
