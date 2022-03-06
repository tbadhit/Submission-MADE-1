package com.tbadhit.core.domain.usecase

import com.tbadhit.core.data.Resource
import com.tbadhit.core.domain.model.User
import com.tbadhit.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun getSearchUsers(query: String?): Flow<Resource<List<User>>> {
        return userRepository.getSearchUsers(query)
    }

    override fun getListFollowers(username: String): Flow<Resource<List<User>>> {
        return userRepository.getListFollower(username)
    }

    override fun getListFollowing(username: String): Flow<Resource<List<User>>> {
        return userRepository.getListFollowing(username)
    }

    override fun getDetailUser(username: String): Flow<Resource<User>> {
        return userRepository.getDetailUser(username)
    }

    override fun getAllFavorites(): Flow<List<User>> {
        return userRepository.getAllFavorites()
    }

    override fun check(id: Int): Flow<User>? {
        return userRepository.check(id)
    }

    override suspend fun insert(
        username: String?,
        id: Int?,
        avatarUrl: String?,
        isFavorite: Boolean?
    ) {
        return userRepository.insert(username, id, avatarUrl, isFavorite)
    }

    override suspend fun delete(user: User): Int {
        return userRepository.delete(user)
    }
}