package com.example.suitmediamobiletest.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.suitmediamobiletest.data.network.ApiService
import com.example.suitmediamobiletest.data.response.DataItem
import com.example.suitmediamobiletest.data.response.Response
import retrofit2.Call
import retrofit2.Callback

class UserRepository private constructor(private val apiService: ApiService) {

    fun getUsers(): LiveData<List<DataItem>> {
        val result = MutableLiveData<List<DataItem>>()

        apiService.getUsers().enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    result.value = response.body()?.data ?: emptyList()
                } else {
                    Log.d(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })

        return result

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