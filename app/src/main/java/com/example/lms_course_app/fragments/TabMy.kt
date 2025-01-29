package com.example.lms_course_app.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.lms_course_app.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lms_course_app.adapters.ActivitiesAdapter
import com.example.lms_course_app.models.ActivityViewModel
import com.example.lms_course_app.utils.ActivityDateGroup

class TabMy : Fragment() {
    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_my, container, false)

        // Найти RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewMy)
        val timeToTrain: TextView = view.findViewById(R.id.timeToTrain)
        val startToTrain: TextView = view.findViewById(R.id.startToTrain)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Создаем адаптер
        val adapter = ActivitiesAdapter(emptyList(), isUserTab = false)
        recyclerView.adapter = adapter

        // Подключаем ViewModel
        activityViewModel = ViewModelProvider(this)[ActivityViewModel::class.java]


        activityViewModel.allActivities.observe(viewLifecycleOwner) { activities ->
            val groupedActivities = ActivityDateGroup.groupActivitiesByDate(activities)
            Log.d("TabMy1", "Grouped activities: $groupedActivities")
            if (groupedActivities.isEmpty()) {
                timeToTrain.visibility = View.VISIBLE
                startToTrain.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                timeToTrain.visibility = View.GONE
                startToTrain.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE

                adapter.updateData(groupedActivities)
            }
        }

        return view
    }
}