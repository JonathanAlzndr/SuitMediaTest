package com.example.suitmediamobiletest.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.suitmediamobiletest.data.network.ApiService
import com.example.suitmediamobiletest.data.response.DataItem

class UserRepository private constructor(private val apiService: ApiService) {


    fun getUsers(): LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 2
            ),
            pagingSourceFactory = { UserPagingSource(apiService) }
        ).liveData
    }

    companion object {
        private const val TAG = "UserRepository"

        @Volatile
        var INSTANCE: UserRepository? = null

        fun getInstance(apiService: ApiService): UserRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = UserRepository(apiService)
                }
            }
            return INSTANCE as UserRepository
        }
    }
}