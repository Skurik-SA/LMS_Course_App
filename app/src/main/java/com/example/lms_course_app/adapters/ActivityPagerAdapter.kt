package com.example.lms_course_app.adapters
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lms_course_app.fragments.TabMy
import com.example.lms_course_app.fragments.TabUsers

class ActivityPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        const val TAB_MY = 0
        const val TAB_USERS = 1
    }

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TAB_MY -> TabMy()
            TAB_USERS -> TabUsers()
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}