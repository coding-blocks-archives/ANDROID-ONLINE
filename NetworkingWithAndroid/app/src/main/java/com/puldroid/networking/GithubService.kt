package com.puldroid.networking

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id")id:String = "abc"): Response<User>

    @GET("search/users")
    suspend fun searchUsers(@Query("q")query: String): Response<UserResponse>
}