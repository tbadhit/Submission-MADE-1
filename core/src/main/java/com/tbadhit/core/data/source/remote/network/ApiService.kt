package com.tbadhit.core.data.source.remote.network

import com.tbadhit.core.data.source.remote.response.ResponseSearch
import com.tbadhit.core.data.source.remote.response.ResponseUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getSearchUser(
        @Query("q") q: String?
    ): ResponseSearch

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String?
    ): ResponseUser

    @GET("users/{username}/followers")
    suspend fun getUserFollower(
        @Path("username") username: String?
    ): List<ResponseUser>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String?
    ): List<ResponseUser>
}