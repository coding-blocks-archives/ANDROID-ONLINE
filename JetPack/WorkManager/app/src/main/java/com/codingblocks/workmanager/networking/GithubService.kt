package com.codingblocks.workmanager.networking

import com.codingblocks.workmanager.modals.User
import retrofit2.Response
import retrofit2.http.GET

interface GithubService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}