package com.example.lms_course_app.utils

import com.example.lms_course_app.models.ActivityModel

object ActivityDateGroup {
    fun groupActivitiesByDate(activities: List<ActivityModel>): List<Any> {
        val grouped = activities.groupBy { it.getRelativeDate() } // Группируем по "Сегодня", "Вчера", итд...
        val result = mutableListOf<Any>()

        for ((date, activityList) in grouped) {
            result.add(date) // Дата
            result.addAll(activityList) // Активности под датой
        }

        return result
    }
}