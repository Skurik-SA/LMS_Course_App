package com.example.lms_course_app.custom_views

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import kotlin.math.abs

class SwipeableMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var maxHeight = 0
    private val minHeight = 320

    private var initialY = 0f
    private var initialHeight = 0

    private var isDragging = false

    private var velocityTracker: VelocityTracker? = null

    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop

    private var currentAnimator: ValueAnimator? = null

    init {
        // Инициализация maxHeight при первом измерении
        post {
            updateMaxHeight()
        }
    }

    /** Метод для обновления максимальной высоты */
    fun updateMaxHeight() {
        // Для изменения высоты при переключении между окнами, вызываю в NewActivityMao
        measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        maxHeight = measuredHeight.coerceAtLeast(minHeight)

        // Обновляем параметры компоновки
        layoutParams.height = maxHeight
        requestLayout()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                // rawY для точного положения
                initialY = ev.rawY
                initialHeight = layoutParams.height

                velocityTracker = VelocityTracker.obtain().apply {
                    addMovement(ev)
                }

                cancelAnimationIfRunning()
                isDragging = false
            }
            MotionEvent.ACTION_MOVE -> {
                velocityTracker?.addMovement(ev)

                val dy = abs(ev.rawY - initialY)

                if (dy > touchSlop) {
                    isDragging = true
                    requestDisallowInterceptTouchEvent(true)
                    return true
                }
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                reset()
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        velocityTracker?.addMovement(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                val dy = (initialY - event.rawY).toInt()
                val newHeight = (initialHeight + dy).coerceIn(minHeight, maxHeight)
                layoutParams.height = newHeight
                requestLayout()
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                velocityTracker?.computeCurrentVelocity(1000)
                val velocityY = velocityTracker?.yVelocity ?: 0f

                handleRelease(velocityY)
                reset()
            }
        }

        if (event.actionMasked == MotionEvent.ACTION_UP) {
            performClick()
        }
        return true
    }

    private fun handleRelease(velocityY: Float) {
        val midHeight = (maxHeight + minHeight) / 2

        when {
            velocityY < -1000 -> { // Если скорость движения вверх больше 200
                // Быстрое движение вверх
                animateToHeight(maxHeight)
            }
            velocityY > 1000 -> { // Если скорость движения вниз больше 200
                // Быстрое движение вниз
                animateToHeight(minHeight)
            }
            layoutParams.height > midHeight -> { // Если высота больше середины
                // Отпущено выше середины, открыть
                animateToHeight(maxHeight)
            }
            else -> {
                // Отпущено ниже середины, закрыть
                animateToHeight(minHeight)
            }
        }
    }

    private fun animateToHeight(targetHeight: Int) {
        cancelAnimationIfRunning()

        currentAnimator = ValueAnimator.ofInt(layoutParams.height, targetHeight).apply {
            duration = 300L
            interpolator = DecelerateInterpolator()
            addUpdateListener { animator ->
                layoutParams.height = animator.animatedValue as Int
                requestLayout()
            }
            start()
        }
    }

    private fun cancelAnimationIfRunning() {
        currentAnimator?.cancel()
        currentAnimator = null
    }

    private fun reset() {
        isDragging = false
        velocityTracker?.recycle()
        velocityTracker = null
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}


