package com.example.lms_course_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lms_course_app.R
import com.example.lms_course_app.databinding.ItemActivityTypeBinding
import com.example.lms_course_app.models.ActivityType

class ActivityTypeAdapter(
    private val activityTypes: List<ActivityType>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ActivityTypeAdapter.ActivityTypeViewHolder>() {

    // Переменная для хранения выбранной позиции
    private var selectedPosition: Int = 0

    inner class ActivityTypeViewHolder(private val binding: ItemActivityTypeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(type: ActivityType, isSelected: Boolean) {
            binding.typeName.text = type.displayName

            // Смена цвета, если выбранная активность в текущей позиции
            val context = binding.root.context
            val borderColor = if (isSelected) {
                ContextCompat.getColor(context, R.color.purple_500) // Цвет выделенной границы
            } else {
                ContextCompat.getColor(context, R.color.gray) // Цвет границы в обычном состоянии
            }

            // Установка цвета и толщины границы
            binding.cardView.strokeColor = borderColor
            binding.cardView.strokeWidth = if (isSelected) 8 else 4

            binding.root.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val previousPosition = selectedPosition
                    selectedPosition = position

                    onItemClick(type.displayName)

                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityTypeViewHolder {
        val binding = ItemActivityTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityTypeViewHolder, position: Int) {
        holder.bind(activityTypes[position], position == selectedPosition)
    }

    override fun getItemCount() = activityTypes.size
}