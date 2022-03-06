package com.tbadhit.core.data.source.remote

import android.util.Log
import com.tbadhit.core.data.source.remote.network.ApiResponse
import com.tbadhit.core.data.source.remote.network.ApiService
import com.tbadhit.core.data.source.remote.response.ResponseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getSearchUsers(query: String?): Flow<ApiResponse<List<ResponseUser>>> =
        flow {
            try {
                val userSearch = apiService.getSearchUser(query)
                val listUserSearch = userSearch.listUsers
                if (listUserSearch.isNullOrEmpty()) {
                    emit(ApiResponse.Error(null))
                } else {
                    emit(ApiResponse.Success(listUserSearch))
                }

            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)


    suspend fun getListFollower(username: String): Flow<ApiResponse<List<ResponseUser>>> =
        flow {
            try {
                val userFollower = apiService.getUserFollower(username)
                emit(ApiResponse.Success(userFollower))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getListFollowing(username: String): Flow<ApiResponse<List<ResponseUser>>> =
        flow {
            try {
                val userFollowing = apiService.getUserFollowing(username)
                emit(ApiResponse.Success(userFollowing))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailUser(username: String): Flow<ApiResponse<ResponseUser>> =
        flow {
            try {
                val userDetail = apiService.getDetailUser(username)
                emit(ApiResponse.Success(userDetail))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)
}