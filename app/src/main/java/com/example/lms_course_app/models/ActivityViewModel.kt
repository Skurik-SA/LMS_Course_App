package com.example.lms_course_app.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lms_course_app.room.db.AppDatabase
import kotlinx.coroutines.launch

class ActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val activityDao = db.activityDao()

    val allActivities: LiveData<List<ActivityModel>> = activityDao.getAllActivities()

    fun insertActivity(activity: ActivityModel) {
        viewModelScope.launch {
            activityDao.insert(activity)
        }
    }
}