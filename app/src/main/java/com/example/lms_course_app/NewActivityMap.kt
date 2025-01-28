package com.example.lms_course_app

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lms_course_app.adapters.ActivityTypeAdapter
import com.example.lms_course_app.databinding.ActivityNewMapBinding

class NewActivityMap : AppCompatActivity() {

    private lateinit var binding: ActivityNewMapBinding

    private var startTime: Long = 0L
    private var handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var timerRunnable: Runnable

    private fun startTimer() {
        timerRunnable = object : Runnable {
            override fun run() {
                val elapsedMillis = System.currentTimeMillis() - startTime
                val hours = (elapsedMillis / (1000 * 60 * 60)) % 24
                val minutes = (elapsedMillis / (1000 * 60)) % 60
                val seconds = (elapsedMillis / 1000) % 60

                // Обновляем TextView с таймером
                binding.timerText.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)

                // Повторяем обновление каждую секунду
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(timerRunnable)
    }

    private fun stopTimer() {
        handler.removeCallbacks(timerRunnable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityTypes = listOf("Велосипед", "Бег", "Шаг", "Ползком", "На корточках")
        var selectedActivity: String? = activityTypes.firstOrNull()

        binding.activityTypesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.activityTypesRecyclerView.adapter = ActivityTypeAdapter(activityTypes) { selectedType ->
            selectedActivity = selectedType
            binding.startButton.visibility = View.VISIBLE
        }

        binding.startButton.setOnClickListener {
            if (selectedActivity != null) {
                startTime = System.currentTimeMillis()
                startTimer()

                // Получаем ссылку на SwipeableMenu
                val swipeableMenu = binding.swipeableMenu

                // Измеряем высоту нового содержимого
                binding.selectedActivityMenu.measure(
                    View.MeasureSpec.makeMeasureSpec(swipeableMenu.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                val targetHeight = binding.selectedActivityMenu.measuredHeight

                // Текущая высота SwipeableMenu
                val initialHeight = swipeableMenu.height

                // Анимируем изменение высоты
                ValueAnimator.ofInt(initialHeight, targetHeight).apply {
                    duration = 300L
                    interpolator = DecelerateInterpolator()
                    addUpdateListener { animator ->
                        val animatedValue = animator.animatedValue as Int
                        swipeableMenu.layoutParams.height = animatedValue
                        swipeableMenu.requestLayout()
                    }
                    doOnEnd {
                        // После завершения анимации переключаем видимость
                        binding.slideMenu.visibility = View.GONE
                        binding.selectedActivityMenu.visibility = View.VISIBLE
                        binding.activityTitle.text = selectedActivity

                        // Обновляем максимальную высоту меню
                        swipeableMenu.updateMaxHeight()
                    }
                    start()
                }
            }
        }

        binding.finishButton.setOnClickListener {
            stopTimer()
        }

        binding.backToActivitiesButton.setOnClickListener {
            val intent = Intent(this, TrainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerRunnable)
    }
}
