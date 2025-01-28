package com.example.lms_course_app
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lms_course_app.databinding.TrainPageBinding
import com.example.lms_course_app.fragments.ActivityFragment
import com.example.lms_course_app.fragments.ProfileFragment

class TrainActivity : AppCompatActivity() {
    private var activeFragment: Fragment? = null
    private lateinit var binding: TrainPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TrainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val activityFragment = ActivityFragment()
            val profileFragment = ProfileFragment()

            supportFragmentManager.beginTransaction()
                .add(binding.fragmentContainerView.id, activityFragment, "ActivityFragment")
                .hide(activityFragment)
                .commit()

            supportFragmentManager.beginTransaction()
                .add(binding.fragmentContainerView.id, profileFragment, "ProfileFragment")
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
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_activity -> {
                    binding.fab.show() // Показываем кнопку FAB
                    switchFragment("ActivityFragment", ActivityFragment())
                }
                R.id.nav_profile -> {
                    binding.fab.hide() // Скрываем кнопку FAB
                    switchFragment("ProfileFragment", ProfileFragment())
                }
            }
            true
        }

        // Установка обработчика кликов для FAB
        binding.fab.setOnClickListener {
            val intent = Intent(this, NewActivityMap::class.java)
            startActivity(intent)
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

