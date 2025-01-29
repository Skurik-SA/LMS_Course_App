package com.example.lms_course_app

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lms_course_app.adapters.ActivityTypeAdapter
import com.example.lms_course_app.databinding.ActivityNewMapBinding
import com.example.lms_course_app.models.ActivityModel
import com.example.lms_course_app.models.ActivityType
import com.example.lms_course_app.models.ActivityViewModel
import kotlin.math.floor
import kotlin.random.Random

class NewActivityMap : AppCompatActivity() {

    private lateinit var binding: ActivityNewMapBinding

    private var startTime: Long = 0L
    private var handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var timerRunnable: Runnable
    private var selectedActivityType: ActivityType? = null
    private val coordinatesList = mutableListOf<Pair<Double, Double>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityTypes = ActivityType.entries // Получаем все enum значения
        selectedActivityType = activityTypes.firstOrNull()

        binding.activityTypesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.activityTypesRecyclerView.adapter = ActivityTypeAdapter(activityTypes) { selectedType ->
            Log.d("NewActivityMap1", "Selected type: $selectedType")
            selectedActivityType = ActivityType.entries.firstOrNull { it.displayName == selectedType }
            Log.d("NewActivityMap1", "Selected activity type: $selectedActivityType")
            binding.startButton.visibility = View.VISIBLE
        }

        binding.startButton.setOnClickListener {
            Log.d("NewActivityMap1", "Start button clicked")
            if (selectedActivityType != null) {
                Log.d("NewActivityMap1", "Selected activity type: $selectedActivityType")
                startTime = System.currentTimeMillis()
                coordinatesList.clear()
                coordinatesList.add(generateRandomCoordinates()) // Генерируем случайные координаты

                startTimer()
                animateMenuTransition()
            }
        }

        binding.finishButton.setOnClickListener {
            stopTimer()
            val endTime = System.currentTimeMillis()

            val newActivity = ActivityModel(
                distance = generateRandomDistance(),
                createdAt = startTime,
                createdBy = "Я",
                activityType = selectedActivityType!!,
                startTime = startTime,
                endTime = endTime,
                coordinates = coordinatesList
            )

            val viewModel = ViewModelProvider(this)[ActivityViewModel::class.java]
            viewModel.insertActivity(newActivity)

            finish()
        }

        binding.backToActivitiesButton.setOnClickListener {
            val intent = Intent(this, TrainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::timerRunnable.isInitialized) {
            handler.removeCallbacks(timerRunnable)
        }
    }

    private fun startTimer() {
        startTime = System.currentTimeMillis()
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

    private fun animateMenuTransition() {
        val swipeableMenu = binding.swipeableMenu
        binding.selectedActivityMenu.measure(
            View.MeasureSpec.makeMeasureSpec(swipeableMenu.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        val targetHeight = binding.selectedActivityMenu.measuredHeight
        val initialHeight = swipeableMenu.height

        ValueAnimator.ofInt(initialHeight, targetHeight).apply {
            duration = 300L
            interpolator = DecelerateInterpolator()
            addUpdateListener { animator ->
                val animatedValue = animator.animatedValue as Int
                swipeableMenu.layoutParams.height = animatedValue
                swipeableMenu.requestLayout()
            }
            doOnEnd {
                binding.slideMenu.visibility = View.GONE
                binding.selectedActivityMenu.visibility = View.VISIBLE
                binding.activityTitle.text = selectedActivityType?.displayName ?: ""
                swipeableMenu.updateMaxHeight()
            }
            start()
        }
    }

    private fun generateRandomDistance(): Float {
        return floor((Random.nextFloat() * (10.0f - 1.0f) + 1.0f) * 100.0f) / 100.0f
    }

    private fun generateRandomCoordinates(): Pair<Double, Double> {
        val lat = 55.75 + (Random.nextDouble() - 0.5) * 0.01
        val lon = 37.61 + (Random.nextDouble() - 0.5) * 0.01
        return lat to lon
    }
}
