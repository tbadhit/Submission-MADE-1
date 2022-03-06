package com.tbadhit.core.utils

import com.tbadhit.core.data.source.local.entity.UserEntity
import com.tbadhit.core.data.source.remote.response.ResponseUser
import com.tbadhit.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapResponsesToDomain(input: List<ResponseUser>): Flow<List<User>> {
        val dataArray = ArrayList<User>()
        input.map { dataUser ->
            val user = User(
                dataUser.id,
                dataUser.username,
                dataUser.company,
                dataUser.publicRepos,
                dataUser.followers,
                dataUser.avatarUrl,
                dataUser.following,
                dataUser.name,
                dataUser.location,
                false
            )
            dataArray.add(user)
        }
        return flowOf(dataArray)
    }

    fun mapResponseToDomain(input: ResponseUser): Flow<User> {
        return flowOf(
            User(
                input.id,
                input.username,
                input.company,
                input.publicRepos,
                input.followers,
                input.avatarUrl,
                input.following,
                input.name,
                input.location,
                false
            )
        )
    }

    fun mapEntitiesToDomain(input: List<UserEntity>): List<User> =
        input.map { userEntity ->
            User(
                id = userEntity.id,
                username = userEntity.username,
                avatarUrl = userEntity.avatarUrl,
                isFavorite = userEntity.isFavorite
            )
        }

    fun mapEntityToDomain(input: UserEntity?): User {
        return User(
            id = input?.id,
            username = input?.username,
            avatarUrl = input?.avatarUrl,
            isFavorite = input?.isFavorite
        )
    }


    fun mapDomainToEntity(username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?) =
        UserEntity(
            id = id,
            username = username,
            avatarUrl = avatarUrl,
            isFavorite = isFavorite
        )
}