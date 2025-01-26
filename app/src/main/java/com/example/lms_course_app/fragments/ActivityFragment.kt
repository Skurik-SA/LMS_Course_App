package com.example.lms_course_app.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.example.lms_course_app.R
import com.example.lms_course_app.adapters.ActivityPagerAdapter
import com.example.lms_course_app.databinding.FragmentActivityBinding

class ActivityFragment : Fragment() {

    private var _binding: FragmentActivityBinding? = null
    private val binding get() = _binding!!

    private val tabTitles by lazy {
        listOf(getString(R.string.my_activities),
               getString(R.string.users_activities))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        // Setup ViewPager
        val adapter = ActivityPagerAdapter(this) // Or rename to TabsPagerAdapter
        viewPager.adapter = adapter

        // Connect TabLayout and ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
