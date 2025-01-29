package com.example.lms_course_app.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: ActivityModel)

    @Query("SELECT * FROM activities ORDER BY start_time DESC")
    fun getAllActivities(): LiveData<List<ActivityModel>>
}