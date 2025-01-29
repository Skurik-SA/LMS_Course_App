package com.example.lms_course_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lms_course_app.R
import com.example.lms_course_app.databinding.FragmentActivityDetailBinding
import com.example.lms_course_app.models.ActivityModel

class ActivityDetailFragment : Fragment() {

    private var _binding: FragmentActivityDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var activity: ActivityModel
    private var isUserTab: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivityDetailBinding.inflate(inflater, container, false)

        // Получаем переданные данные
        arguments?.let {
            activity = it.getSerializable("activity") as ActivityModel
            isUserTab = it.getBoolean("isUserTab")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActivityInfo()
        setupActivityVisibilityData()
        setupActivityListeners()
    }

    private fun setupActivityInfo() {
        // Верхнее меню
        binding.textActivityTitle.text = activity.activityType.toString()
        binding.textUserNameCreatedBy.text = if (isUserTab) "@${activity.createdBy}" else ""

        // Данные активности
        binding.textDistance.text = "${activity.distance} км"
        binding.textTimeAgo.text = activity.getFormattedTimeAgo()
        binding.activityDurationTime.text = activity.getFormattedActivityTime()
        binding.textStartTime.text = getString(R.string.start, activity.getFormattedStartTime())
        binding.textEndTime.text = getString(R.string.finish, activity.getFormattedEndTime())
    }

    private fun setupActivityVisibilityData() {
        // Убираем или показываем элементы в зависимости от вкладки
        if (isUserTab) {
            binding.iconDelete.visibility = View.GONE
            binding.iconShare.visibility = View.GONE
            binding.textUserNameCreatedBy.visibility = View.VISIBLE
            binding.textUserNameCreatedBy.text = "@${activity.createdBy}"
        } else {
            binding.iconDelete.visibility = View.VISIBLE
            binding.iconShare.visibility = View.VISIBLE
            binding.textUserNameCreatedBy.visibility = View.GONE
        }
    }

    private fun setupActivityListeners() {
        // Обработчик нажатия на кнопку назад
        binding.backToActivitiesButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(activity: ActivityModel, isUserTab: Boolean): ActivityDetailFragment {
            val fragment = ActivityDetailFragment()
            val args = Bundle()
            args.putSerializable("activity", activity) // Передаём объект через Serializable
            args.putBoolean("isUserTab", isUserTab)
            fragment.arguments = args
            return fragment
        }
    }
}
