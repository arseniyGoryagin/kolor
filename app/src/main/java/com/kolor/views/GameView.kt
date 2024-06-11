package com.kolor.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.kolor.R
import com.kolor.data.constants.Customizations
import com.kolor.utility.ColorResolver


data class Circle (val x : Float, val y: Float, var alpha : Float)

class GameView(context : Context, attrs : AttributeSet)  : View(context, attrs){


    private var circles = mutableListOf<Circle>()


    private var _clickColor : Int = 0
        set(value){
            paint.color = value
        }


    private var _isBoosted = false


    private val paint = Paint().apply {
        this.color = Color.CYAN
        isAntiAlias = true
    }


    fun setClickColor(color : Int) {
        val colorId = ColorResolver.resolve(color)

        if(colorId !=null) {
            _clickColor = ContextCompat.getColor(context, colorId)
        }
        else{
            _clickColor = ContextCompat.getColor(context, R.color.purple_click)
        }
    }


    fun setIsBoosted(isBoosted : Boolean){
            _isBoosted = isBoosted
    }

    // TODO set is boosted

    fun drawBoosted(canvas : Canvas){
        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()
        val colors  = intArrayOf(
            Color.TRANSPARENT,
            paint.color
        )

        val bottomShader = LinearGradient(0F, height-400, 0F, height, colors, null, Shader.TileMode.CLAMP)
        paint.shader = bottomShader
        canvas.drawRect(0f, 0f, width, height, paint)

        /*
        val leftShader = LinearGradient(0F, height-400, 0F, height, colors, null, Shader.TileMode.CLAMP)
        paint.shader = leftShader
        canvas.drawRect(0f, 0f, width, height, paint)


        val bottomShader = LinearGradient(0F, height-400, 0F, height, colors, null, Shader.TileMode.CLAMP)
        paint.shader = bottomShader
        canvas.drawRect(0f, 0f, width, height, paint)

        val bottomShader = LinearGradient(0F, height-400, 0F, height, colors, null, Shader.TileMode.CLAMP)
        paint.shader = bottomShader
        canvas.drawRect(0f, 0f, width, height, paint)*/


        paint.shader = null
    }


    fun drawCircles(canvas : Canvas){
        for( circle in circles){
            paint.alpha = (circle.alpha * 255).toInt()
            canvas.drawCircle(circle.x, circle.y, 75F, paint)
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*if(_isBoosted){
        drawBoosted(canvas)
        }*/

        drawCircles(canvas)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
                    MotionEvent.ACTION_DOWN -> {

                        addCircle(event.x, event.y)
                        return true

                    }
                    MotionEvent.ACTION_UP ->{

                        performClick()
                        return true
                    }
        }

        return super.onTouchEvent(event)
    }


    fun animateCircle(circle : Circle){


        val animator = ValueAnimator.ofFloat(1F, 0F)

        animator.duration = 1000;
        animator.addUpdateListener {


                circle.alpha = it.animatedValue as Float
                invalidate()


        }

        animator.addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation : Animator){
                        circles.remove(circle)
                        invalidate()
                }
        })
        animator.start()
    }


    fun addCircle(x : Float, y : Float){

        val circle = Circle(x, y, 1F)
        circles.add(circle)
        animateCircle(circle)
        invalidate()
    }


}