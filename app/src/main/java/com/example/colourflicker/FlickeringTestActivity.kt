package com.example.colourflicker

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout

class FlickeringTestActivity : Activity() {
    private var alternate = false
    private var isPaused = true
    private val flickerView: FlickerView by lazy { FlickerView(this) }
    private val pauseButton: Button by lazy { Button(this) }
    private val resumeButton: Button by lazy { Button(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Create a RelativeLayout to hold the View and buttons
        val relativeLayout = RelativeLayout(this)

        relativeLayout.addView(flickerView)

        resumeButton.text = "start"
        val paramsResume = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        paramsResume.addRule(RelativeLayout.ALIGN_PARENT_START)
        paramsResume.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        paramsResume.bottomMargin = 200
        paramsResume.leftMargin = 200
        paramsResume.rightMargin = 10
        resumeButton.layoutParams = paramsResume
        resumeButton.setOnClickListener {
            isPaused = false
            flickerView.invalidate() // Resume animation
        }
        relativeLayout.addView(resumeButton)

        pauseButton.text = "stop"
        val paramsPause = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        paramsPause.addRule(RelativeLayout.ALIGN_PARENT_END)
        paramsPause.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        paramsPause.bottomMargin = 200
        paramsPause.rightMargin = 200
        paramsPause.leftMargin = 10
        pauseButton.layoutParams = paramsPause
        pauseButton.setOnClickListener {
            isPaused = true
        }
        relativeLayout.addView(pauseButton)

        // Set the content view to the RelativeLayout
        setContentView(relativeLayout)
    }

    inner class FlickerView(context: Context) : View(context) {
        private val paint1 = Paint()
        private val paint2 = Paint()

        init {
            paint1.color = Color.rgb(72, 114, 152)
            paint2.color = Color.rgb(95, 142, 152)
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            canvas.drawColor(Color.rgb(85,128,170))

                val screenWidth = width.toFloat()
                val screenHeight = height.toFloat()

                val rect1X = if (alternate) (screenWidth / 2) - 200f else (screenWidth / 2)
                val rect2X = if (alternate) (screenWidth / 2) else (screenWidth / 2) - 200f

                canvas.drawRect(rect1X, screenHeight / 2 - 100f, rect1X + 200f, screenHeight / 2 + 100f, paint1)
                canvas.drawRect(rect2X, screenHeight / 2 - 100f, rect2X + 200f, screenHeight / 2 + 100f, paint2)

                alternate = !alternate

            if(!isPaused) {
                invalidate() // Redraw the view
            }
        }
    }
}
