package com.example.lms_course_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lms_course_app.R
import com.example.lms_course_app.adapters.ActivitiesAdapter
import com.example.lms_course_app.models.ActivityModel
import com.example.lms_course_app.utils.ActivityDateGroup

class TabUsers : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_users, container, false)

        // Найти RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewUsers)
        val textNoActivities: TextView = view.findViewById(R.id.textNoActivities)

//        var activities = listOf<ActivityModel>() // Пока пустой список для проверки
//
//        // Подготовить данные
//        activities = listOf(
//            ActivityModel(5.0f, 3600, System.currentTimeMillis() / 1000, System.currentTimeMillis() / 1000 + 1000,"Бег", System.currentTimeMillis() / 1000, "Я"),
//            ActivityModel(10.0f, 5400, System.currentTimeMillis() / 1000, System.currentTimeMillis() / 1000 + 1000, "Велосипед", System.currentTimeMillis() / 1000 - 86400, "Мария"),
//            ActivityModel(3.5f, 1800, System.currentTimeMillis() / 1000, System.currentTimeMillis() / 1000 + 1000,"Прогулка", System.currentTimeMillis() / 1000 - 2 * 86400, "Андрей")
//        )
//
//        val groupedActivities = ActivityDateGroup.groupActivitiesByDate(activities)
//
//        if (groupedActivities.isEmpty()) {
//            textNoActivities.visibility = View.VISIBLE
//            recyclerView.visibility = View.GONE
//        }
//        else {
//            textNoActivities.visibility = View.GONE
//            recyclerView.visibility = View.VISIBLE
//
//            // Устанавливаем LayoutManager и адаптер
//            recyclerView.layoutManager = LinearLayoutManager(requireContext())
//            recyclerView.adapter = ActivitiesAdapter(groupedActivities, isUserTab = true)
//        }

        return view
    }
}