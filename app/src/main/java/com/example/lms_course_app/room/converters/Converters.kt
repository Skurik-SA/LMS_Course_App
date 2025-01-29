package com.example.lms_course_app.room.converters

import androidx.room.TypeConverter
import com.example.lms_course_app.models.ActivityType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromActivityType(type: ActivityType): String = type.name

    @TypeConverter
    fun toActivityType(value: String): ActivityType = ActivityType.valueOf(value)

    @TypeConverter
    fun fromCoordinatesList(coordinates: List<Pair<Double, Double>>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<Pair<Double, Double>>>() {}.type
        return gson.toJson(coordinates, type)
    }

    @TypeConverter
    fun toCoordinatesList(coordinatesString: String?): List<Pair<Double, Double>>? {
        val gson = Gson()
        val type = object : TypeToken<List<Pair<Double, Double>>>() {}.type
        return gson.fromJson(coordinatesString, type)
    }
}