package com.example.suitmediamobiletest.data.di

import com.example.suitmediamobiletest.data.UserRepository
import com.example.suitmediamobiletest.data.network.ApiConfig

object Injection {
    fun provideRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}