package com.example.lms_course_app.models

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


// Нужно определиться с тем как время указывать, а то кринж какой-то.
// С одной стороны типа норм умножать на 1000, с другой стороны это не всегда очевидно...
data class ActivityModel(
    val distance: Float, // Расстояние
    val time: Long,       // Время активности (в секундах)
    val startTime: Long, // Время старта активности в миллисекундах
    val endTime: Long, // Время окончания активности в миллисекундах
    val activityType: String, // Тип активности (например, "Бег")
    val createdAt: Long,      // Время создания активности (Unix timestamp)
    val createdBy: String     // Ник пользователя
) : Serializable {
    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return formatter.format(Date(createdAt * 1000))
    }

    fun getFormattedTimeAgo(): String {
        val now = System.currentTimeMillis() / 1000
        val timeDiff = now - createdAt

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
                dateFormatter.format(Date(createdAt * 1000))
            }
        }
    }

    // Форматируем время старта
    fun getFormattedStartTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Date(startTime * 1000))
    }

    // Форматируем время окончания
    fun getFormattedEndTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Date(endTime * 1000))
    }

    fun getFormattedActivityTime(): String {
        val activityDurationTime = endTime - startTime
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
        val activityDate = Calendar.getInstance().apply { timeInMillis = createdAt * 1000 }

        return when {
            now.get(Calendar.YEAR) == activityDate.get(Calendar.YEAR) &&
                    now.get(Calendar.DAY_OF_YEAR) == activityDate.get(Calendar.DAY_OF_YEAR) -> "Сегодня"
            now.get(Calendar.YEAR) == activityDate.get(Calendar.YEAR) &&
                    now.get(Calendar.DAY_OF_YEAR) - activityDate.get(Calendar.DAY_OF_YEAR) == 1 -> "Вчера"
            else -> getFormattedDate()
        }
    }
}
