package com.kolor.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.os.IResultReceiver._Parcel
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.kolor.R
import com.kolor.utility.ColorResolver

class CircularProgress(context : Context, attrs : AttributeSet) : View(context, attrs) {



    // PAINT --------------------------------------------------------------------
    private val progressPaint = Paint().apply {
        this.color = _progressColor
        isAntiAlias = true
    }

    private val backgroundPaint = Paint().apply {
        this.color = ContextCompat.getColor(context, android.R.color.darker_gray)
        isAntiAlias = true
    }

    private val innerBackgroundPaint = Paint().apply {
        this.color = ContextCompat.getColor(context, android.R.color.darker_gray)
        isAntiAlias = true
    }
    private val _textPaint = Paint().apply {
        this.color = ContextCompat.getColor(context, R.color.purple_click)
        isAntiAlias = true
    }

    private var _progressColor : Int = 0
        set(value) {
            val colordId = ColorResolver.resolve(value)
            if(colordId != null){
                progressPaint.color = ContextCompat.getColor(context, colordId)
            }else{
                progressPaint.color = ContextCompat.getColor(context, R.color.gold_progress)
            }
        }
    private var _backgroundMainColor : Int = 0
        set(value) {
            val colordId = ColorResolver.resolve(value)
            if(colordId != null){
                backgroundPaint.color= ContextCompat.getColor(context, colordId)
            }else{
                backgroundPaint.color = ContextCompat.getColor(context, R.color.main_black)
            }
        }
    private var _innerBackground : Int = 0
        set(value) {
            val colordId = ColorResolver.resolve(value)
            if(colordId != null){
                innerBackgroundPaint.color = ContextCompat.getColor(context, colordId)
            }else{
                innerBackgroundPaint.color = ContextCompat.getColor(context, R.color.main_black)
            }

        }
    private var _text : Int = 0
        set(value) {
            val colordId = ColorResolver.resolve(value)
            if(colordId != null){
                _textPaint.color = ContextCompat.getColor(context, colordId)
            }else{
                _textPaint.color = ContextCompat.getColor(context, R.color.purple_click)
            }

        }




    // VARIABLES ----------------------------------------------------------------
    private var _progress : Int = 0
    private var _isBoosted : Boolean = false
    private val _rect = RectF()

    private var _textSize = 0
    private val BOOSTED_TEXT = "2x"






    // SETTERS ------------------------------------------------------
    fun setProgress(newProgress : Long){
        _progress = newProgress.toInt()
        invalidate()}
    fun setProgressColor(newColor : Int) {
        _progressColor = newColor
        invalidate()
    }
    fun setBackgroundMainColor(newColor : Int) {
        _backgroundMainColor = newColor
        invalidate()}
    fun setInnerBackground(newColor: Int){
        _innerBackground = newColor
        invalidate()
    }

    fun setIsBoosted(value : Boolean){
        _isBoosted = value
    }




    //  DRAW -----------------------------------------------------------------

    fun drawCircle(canvas : Canvas){
        canvas.drawOval(_rect, backgroundPaint)
    }

    fun drawInnerCircle(canvas : Canvas){
        val padding = 30f
        val innerRect = RectF(_rect.left + padding, _rect.top + padding, _rect.right - padding , _rect.bottom  - padding)
        canvas.drawOval(innerRect, innerBackgroundPaint)
    }

    fun drawProgress(canvas : Canvas){
        val maxAngle = 360F
        val sweepAngle = (_progress / 100F) * maxAngle
        canvas.drawArc(_rect, -90F,  sweepAngle, true,  progressPaint)
    }

    fun drawBoosted(canvas: Canvas){

        val centerX = _rect.centerX()
        val centerY = _rect.centerY()

        canvas.drawText(BOOSTED_TEXT, centerX, centerY, _textPaint )

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle(canvas)
        drawProgress(canvas)
        drawInnerCircle(canvas)
        if(_isBoosted){
            drawBoosted(canvas)
        }
    }






    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        _rect.set(0F, 0F, w.toFloat(), h.toFloat())
    }





}