package com.example.lms_course_app
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lms_course_app.fragments.ActivityFragment
import com.example.lms_course_app.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class TrainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.train_page)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, ActivityFragment(), "ActivityFragment")
                .commit()
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_activity -> {
                    switchToFragment("ActivityFragment") { ActivityFragment() }
                    true
                }
                R.id.nav_profile -> {
                    switchToFragment("ProfileFragment") { ProfileFragment() }
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Метод для переключения между фрагментами
     */
    private fun switchToFragment(tag: String, fragmentInstance: () -> Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        // Скрыть все текущие фрагменты
        fragmentManager.fragments.forEach { fragment ->
            if (fragment.isVisible) {
                transaction.hide(fragment)
            }
        }

        var fragment = fragmentManager.findFragmentByTag(tag)

        if (fragment != null) {
            transaction.show(fragment)
        } else {
            fragment = fragmentInstance()
            transaction.add(R.id.fragmentContainerView, fragment, tag)
        }

        transaction.commit()
    }
}

