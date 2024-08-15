package com.example.suitmediamobiletest.data.network

import com.example.suitmediamobiletest.data.response.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): Call<Response>
}