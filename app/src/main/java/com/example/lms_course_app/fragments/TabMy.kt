package com.example.lms_course_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.lms_course_app.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lms_course_app.adapters.ActivitiesAdapter
import com.example.lms_course_app.models.ActivityModel
import com.example.lms_course_app.utils.ActivityDateGroup

class TabMy : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_my, container, false)

        // Найти RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewMy)
        val timeToTrain: TextView = view.findViewById(R.id.timeToTrain)
        val startToTrain: TextView = view.findViewById(R.id.startToTrain)

        // Подготовить данные
        var activities = listOf<ActivityModel>() // Пока пустой список для проверки

        // Список активностей... Закоментить для проверки плейхослдера
        activities = listOf(
            ActivityModel(5.2f, 3620, System.currentTimeMillis() / 1000 - 3000, System.currentTimeMillis() / 1000, "Бег", System.currentTimeMillis() / 1000, "Я"),
            ActivityModel(1.76f, 340, System.currentTimeMillis() / 1000 - 500, System.currentTimeMillis() / 1000, "Плавание", System.currentTimeMillis() / 1000 - 2000, "Я"),
            ActivityModel(12.01f, 13600, System.currentTimeMillis() / 1000 - 10000, System.currentTimeMillis() / 1000 - 5000, "Сон", System.currentTimeMillis() / 1000 - 10000, "Я"),
            ActivityModel(15.24f, 7800, System.currentTimeMillis() / 1000 - 2000000, System.currentTimeMillis() / 1000 - 10000, "Кроватка", System.currentTimeMillis() / 1000 - 20000, "Я"),
            ActivityModel(22.0f, 1100, System.currentTimeMillis() / 1000 - 45000, System.currentTimeMillis() / 1000 - 10000, "Гладил кошку неспеша", System.currentTimeMillis() / 1000 - 45000, "Я"),
            ActivityModel(25.0f, 1600, System.currentTimeMillis() / 1000 - 80000, System.currentTimeMillis() / 1000 - 10000, "Гладил кошку неспеша", System.currentTimeMillis() / 1000 - 80000, "Я"),
            ActivityModel(21.0f, 3234, System.currentTimeMillis() / 1000 - 85000, System.currentTimeMillis() / 1000 - 10000, "Гладил кошку неспеша", System.currentTimeMillis() / 1000 - 85000, "Я"),
            ActivityModel(1200.0f, 35010, System.currentTimeMillis() / 1000 - 90000, System.currentTimeMillis() / 1000 - 10000, "Гладил кошку неспеша", System.currentTimeMillis() / 1000 - 90000, "Я"),
            ActivityModel(10.0f, 15400, System.currentTimeMillis() / 1000 - 86400, System.currentTimeMillis() / 1000 - 10000, "Велосипед", System.currentTimeMillis() / 1000 - 86400, "Я"),
            ActivityModel(3.5f, 1800, System.currentTimeMillis() / 1000 - 2 * 86400, System.currentTimeMillis() / 1000 - 2 * 10400 + 3600, "Прогулка", System.currentTimeMillis() / 1000 - 2 * 86400, "Я")
        )

        val groupedActivities = ActivityDateGroup.groupActivitiesByDate(activities)

        if (groupedActivities.isEmpty()) {
            // Если данных нет, показать текст и скрыть RecyclerView
            timeToTrain.visibility = View.VISIBLE
            startToTrain.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            // Если данные есть, показать RecyclerView и скрыть текст
            timeToTrain.visibility = View.GONE
            startToTrain.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            // Устанавливаем LayoutManager и адаптер
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = ActivitiesAdapter(groupedActivities, isUserTab = false)
        }
        return view
    }
}