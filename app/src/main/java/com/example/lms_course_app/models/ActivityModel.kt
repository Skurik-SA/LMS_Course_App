package com.example.lms_course_app.models

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


// Нужно определиться с тем как время указывать, а то кринж какой-то.
// С одной стороны типа норм умножать на 1000, с другой стороны это не всегда очевидно...
@Entity(tableName = "activities")
data class ActivityModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val distance: Float, // Расстояние
    @ColumnInfo(name = "start_time") val startTime: Long, // Время старта активности в миллисекундах
    @ColumnInfo(name = "end_time") val endTime: Long, // Время окончания активности в миллисекундах
    @ColumnInfo(name = "activity_type")val activityType: ActivityType, // Тип активности (например, "Бег")
    val createdAt: Long,      // Время создания активности (Unix timestamp)
    val createdBy: String,     // Ник пользователя
    @ColumnInfo(name = "coordinates") val coordinates: List<Pair<Double, Double>>
) : Serializable {

    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return formatter.format(Date(createdAt))
    }

    fun getFormattedTimeAgo(): String {
        val now = System.currentTimeMillis()
        Log.d("time_log", "now: $now и $id")
        val timeDiff = (now - createdAt) / 1000
        Log.d("time_log", "created: $createdAt и $id")
        Log.d("time_log", "diff: $timeDiff и $id")
        return when {
            timeDiff < 60 -> "Только что" // Меньше 1 минуты
            timeDiff < 3600 -> {
                // Меньше 1 часа
                val minutes = timeDiff / 60
                "$minutes ${getMinuteWord(minutes)} назад"
            }
            timeDiff < 86400 -> {
                // Меньше 1 дня
                val hours = timeDiff / 3600
                "$hours ${getHourWord(hours)} назад"
            }
            else -> {
                // Больше 1 дня
                val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                dateFormatter.format(Date(createdAt))
            }
        }
    }

    // Форматируем время старта
    fun getFormattedStartTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Date(startTime))
    }

    // Форматируем время окончания
    fun getFormattedEndTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Date(endTime))
    }

    fun getFormattedActivityTime(): String {
        val activityDurationTime = (endTime - startTime) / 1000
        val hours = activityDurationTime / 3600
        val minutes = (activityDurationTime % 3600) / 60

        return when {
            hours > 0 && minutes > 0 -> "$hours ${getHourWord(hours)} $minutes ${getMinuteWord(minutes)}"
            hours > 0 -> "$hours ${getHourWord(hours)}"
            minutes > 0 -> "$minutes ${getMinuteWord(minutes)}"
            else -> "Меньше минуты"
        }
    }

    private fun getHourWord(hours: Long): String {
        return when {
            hours % 10 == 1L && hours % 100 != 11L -> "час"
            hours % 10 in 2..4 && hours % 100 !in 12..14 -> "часа"
            else -> "часов"
        }
    }

    private fun getMinuteWord(minutes: Long): String {
        return when {
            minutes % 10 == 1L && minutes % 100 != 11L -> "минута"
            minutes % 10 in 2..4 && minutes % 100 !in 12..14 -> "минуты"
            else -> "минут"
        }
    }


    fun getRelativeDate(): String {
        val now = Calendar.getInstance()
        val activityDate = Calendar.getInstance().apply { timeInMillis = createdAt }

        return when {
            now.get(Calendar.YEAR) == activityDate.get(Calendar.YEAR) &&
                    now.get(Calendar.DAY_OF_YEAR) == activityDate.get(Calendar.DAY_OF_YEAR) -> "Сегодня"
            now.get(Calendar.YEAR) == activityDate.get(Calendar.YEAR) &&
                    now.get(Calendar.DAY_OF_YEAR) - activityDate.get(Calendar.DAY_OF_YEAR) == 1 -> "Вчера"
            else -> getFormattedDate()
        }
    }
}
