package com.example.suitmediamobiletest.ui.thirdscreen

import androidx.lifecycle.ViewModel
import com.example.suitmediamobiletest.data.UserRepository

class ThirdViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUsers() = userRepository.getUsers()
}