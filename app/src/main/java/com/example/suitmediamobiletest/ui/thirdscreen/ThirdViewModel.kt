package com.example.suitmediamobiletest.ui.thirdscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.suitmediamobiletest.data.UserRepository

class ThirdViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUsers() = userRepository.getUsers().cachedIn(viewModelScope)
}