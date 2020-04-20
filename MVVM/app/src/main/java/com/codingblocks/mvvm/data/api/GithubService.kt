package com.codingblocks.mvvm.data.api

import com.codingblocks.mvvm.data.models.SearchResponse
import com.codingblocks.mvvm.data.models.User
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("search/users")
    suspend fun searchUser(@Query("q") name: String): Response<SearchResponse>

}
