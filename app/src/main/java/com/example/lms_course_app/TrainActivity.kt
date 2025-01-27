package com.example.lms_course_app
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lms_course_app.fragments.ActivityFragment
import com.example.lms_course_app.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TrainActivity : AppCompatActivity() {
    private var activeFragment: Fragment? = null
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.train_page)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        fab = findViewById(R.id.fab)


        if (savedInstanceState == null) {
            // Инициализация с "ActivityFragment"
            val activityFragment = ActivityFragment()
            val profileFragment = ProfileFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, activityFragment, "ActivityFragment")
                .hide(activityFragment)
                .commit()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, profileFragment, "ProfileFragment")
                .hide(profileFragment)
                .commit()

            // Устанавливаем стартовый фрагмент
            activeFragment = activityFragment
            supportFragmentManager.beginTransaction().show(activeFragment!!).commit()
        } else {
            // Восстановление активного фрагмента после пересоздания Activity
            activeFragment = supportFragmentManager.fragments.find { !it.isHidden }
        }

        // Установка обработчика кликов для BottomNavigationView
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_activity -> {
                    fab.show() // Показываем кнопку FAB
                    switchFragment("ActivityFragment", ActivityFragment())
                }
                R.id.nav_profile -> {
                    fab.hide() // Скрываем кнопку FAB
                    switchFragment("ProfileFragment", ProfileFragment())
                }
            }
            true
        }
    }

    /**
     * Метод для переключения между фрагментами
     */
    private fun switchFragment(tag: String, newFragment: Fragment): Boolean {
        val transaction = supportFragmentManager.beginTransaction()

        // Удаляем все дочерние фрагменты текущего фрагмента (если есть)
        supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)

        // Находим фрагмент по тегу
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            // Скрываем текущий фрагмент и показываем новый
            activeFragment?.let { transaction.hide(it) }
            transaction.show(fragment)
        } else {
            // Добавляем новый фрагмент
            activeFragment?.let { transaction.hide(it) }
            transaction.add(R.id.fragmentContainerView, newFragment, tag)
        }

        transaction.commit()
        activeFragment = fragment ?: newFragment
        return true
    }
}

