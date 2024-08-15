package com.example.suitmediamobiletest.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.suitmediamobiletest.data.UserRepository
import com.example.suitmediamobiletest.data.di.Injection
import com.example.suitmediamobiletest.ui.thirdscreen.ThirdViewModel

class ViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdViewModel::class.java)) {
            return ThirdViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }


    companion object {

        @Volatile
        var INSTANCE: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository())
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}