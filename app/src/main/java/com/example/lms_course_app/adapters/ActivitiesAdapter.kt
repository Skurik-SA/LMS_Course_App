package com.example.lms_course_app.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lms_course_app.R
import com.example.lms_course_app.databinding.ItemActivityBinding
import com.example.lms_course_app.databinding.ItemDateSectionBinding
import com.example.lms_course_app.fragments.ActivityDetailFragment
import com.example.lms_course_app.models.ActivityModel


class ActivitiesAdapter(
    private val items: List<Any>, // Данные: активности и секции с датами
    private val isUserTab: Boolean // Указывает, это вкладка "Пользователи" или "Мои"
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ACTIVITY = 0
        private const val VIEW_TYPE_DATE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ActivityModel -> VIEW_TYPE_ACTIVITY
            is String -> VIEW_TYPE_DATE
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ACTIVITY -> {
                val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ActivityViewHolder(binding, isUserTab)
            }
            VIEW_TYPE_DATE -> {
                val binding = ItemDateSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DateViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DateViewHolder -> {
                val date = items[position] as String
                holder.bind(date)
            }
            is ActivityViewHolder -> {
                val activity = items[position] as ActivityModel
                holder.bind(activity)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    // ViewHolder для активности
    class ActivityViewHolder(
        private val binding: ItemActivityBinding,
        private val isUserTab: Boolean
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: ActivityModel) {
            binding.textDistance.text = "${activity.distance} км"
            binding.textTime.text = activity.getFormattedActivityTime()
            binding.textActivityType.text = activity.activityType
            binding.textCreatedAt.text = activity.getFormattedTimeAgo()

            // Отображаем ник пользователя только на вкладке "Пользователи"
            if (isUserTab) {
                binding.textUserName.visibility = View.VISIBLE
                binding.textUserName.text = "@${activity.createdBy}"
            } else {
                binding.textUserName.visibility = View.GONE
            }

            // Установка плейсхолдера иконки активности (Потом наверное как-то надо будет через data class делать, хз пока что)
            binding.iconActivity.setImageResource(R.drawable.profile_off_ico)

            binding.root.setOnClickListener {
                val fragment = ActivityDetailFragment.newInstance(activity, isUserTab)
                val activityContext = it.context
                if (activityContext is AppCompatActivity) {
                    activityContext.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    // ViewHolder для секции с датой
    class DateViewHolder(private val binding: ItemDateSectionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(date: String) {
            binding.textDateSection.text = date
        }
    }
}
