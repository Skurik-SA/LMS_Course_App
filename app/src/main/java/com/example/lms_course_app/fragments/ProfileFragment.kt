package com.example.lms_course_app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lms_course_app.R
import com.example.lms_course_app.WelcomePageActivity
import com.example.lms_course_app.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Переход на экран изменения пароля
        binding.profileChangePasswordButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, ChangePasswordFragment())
                .addToBackStack(null) // Добавляем транзакцию в back stack
                .commit()
        }

        // Выход из профиля
        binding.buttonLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        clearUserData()

        // Переход на экран авторизации с очисткой стека активностей
        val intent = Intent(requireContext(), WelcomePageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun clearUserData() {
        // Пример очистки SharedPreferences (что-то нашёл, если не нужно будет удалю...)
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", 0)
        sharedPreferences.edit().clear().apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}