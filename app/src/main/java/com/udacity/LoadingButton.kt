package com.udacity


import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.Align.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var buttonBasicColor = Color.parseColor("#07C2AA")
    private var buttonSecondColor = Color.parseColor("#004349")
    private var text ="Download"
    private var progress = 0f



    private var valueAnimator = ValueAnimator.ofFloat(0f,1f)
        .apply{
            duration = 2000
            repeatCount = ValueAnimator.INFINITE

            interpolator = LinearInterpolator()
            addUpdateListener {
                      progress = it.animatedValue as Float
                invalidate()
            }
        }

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        widthSize=w
        heightSize=h
    }

    private val paintSec = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = buttonSecondColor
        style = Paint.Style.FILL
        textAlign = CENTER
        textSize = 55.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }
       private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = buttonBasicColor
        style = Paint.Style.FILL
        textAlign = CENTER
        textSize = 55.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }
    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 55.0f
        textAlign = CENTER
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    }


    init {

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f,0f,widthSize*progress,heightSize.toFloat(),paintSec)
        canvas?.drawRect(0f,0f,widthSize.toFloat(),heightSize.toFloat(),paint)
        val textStartPadding = width.toFloat()/2f
        val textTopPadding = height.toFloat()/2f
        canvas?.drawText(text,textStartPadding,textTopPadding,textPaint)


    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }



}