package com.tbadhit.core.domain.usecase

import com.tbadhit.core.data.Resource
import com.tbadhit.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    // Remote
    fun getSearchUsers(query: String?): Flow<Resource<List<User>>>

    fun getListFollowers(username: String): Flow<Resource<List<User>>>

    fun getListFollowing(username: String): Flow<Resource<List<User>>>

    fun getDetailUser(username: String): Flow<Resource<User>>

    // Local
    fun getAllFavorites(): Flow<List<User>>

    fun check(id: Int): Flow<User>?

    suspend fun insert(username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?)

    suspend fun delete(user: User): Int


}