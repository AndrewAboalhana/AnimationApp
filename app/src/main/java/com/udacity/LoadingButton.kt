package com.udacity


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.Align.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import kotlinx.android.synthetic.main.content_detail.view.*
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
    private var buttonRec1 = RectF()
    private var buttonRec2 = RectF()
    private var textRec = Rect()
    private var circleColor =Color.parseColor("#F9A825")
    private var textWidth = 0f


    private var valueAnimator = ValueAnimator.ofFloat(0f,1f)
        .apply{
            duration = 2000
            repeatCount = ValueAnimator.INFINITE

            interpolator = LinearInterpolator()
            addUpdateListener {
                      progress = it.animatedValue as Float
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {


                }

                override fun onAnimationEnd(animation: Animator) {
                    buttonState = ButtonState.Completed
                    this@LoadingButton.isEnabled = true
                }
            })



        }

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

     when(new){
         ButtonState.Loading ->{
             invalidate()
         }

         ButtonState.Clicked ->{
             text = "We are Loading"
             valueAnimator.start()
             invalidate()
         }
         ButtonState.Completed->{
             valueAnimator.cancel()
             progress=0f
             text="Download"
          invalidate()
         }


     }


    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        widthSize=w
        heightSize=h
    }

    private val paintSec = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = buttonSecondColor
        style = Paint.Style.FILL
    }
       private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = buttonBasicColor
        style = Paint.Style.FILL

    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = LEFT
        textSize = 50f
        typeface = Typeface.DEFAULT_BOLD
    }

    private val circle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = circleColor
    }


    init {

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        buttonRec1.set(0f,0f,widthSize.toFloat(),heightSize.toFloat())
        canvas?.drawRect(buttonRec1,paint)

        textWidth = textPaint.measureText(text)

        canvas?.drawText(text,
            (widthSize-textWidth)/2f,
            (heightSize - (textPaint.ascent()+textPaint.descent()))/2f
            ,textPaint)

        if (buttonState == ButtonState.Clicked ) {


            buttonRec2.set(0f, 0f, widthSize * progress, heightSize.toFloat())
            canvas?.drawRect(buttonRec2, paintSec)

            textWidth = textPaint.measureText(text)

            canvas?.drawText(text,(widthSize-textWidth)/2f,(heightSize - (textPaint.ascent()+textPaint.descent()))/2f,textPaint)


            textPaint.getTextBounds(text, 0, text.length, textRec)
            val textRadius = textRec.height().toFloat()

            canvas?.translate(
                (widthSize + textWidth + textRadius) / 2f,
                heightSize / 2f - textRadius / 2)


            canvas?.drawArc(0f, 0f, textRadius, textRadius,
                0f,
                360 * progress,
                true,
                circle)
        }



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