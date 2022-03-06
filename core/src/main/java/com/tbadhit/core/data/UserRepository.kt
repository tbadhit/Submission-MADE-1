package com.tbadhit.core.data

import com.tbadhit.core.data.source.local.LocalDataSource
import com.tbadhit.core.data.source.remote.RemoteDataSource
import com.tbadhit.core.data.source.remote.network.ApiResponse
import com.tbadhit.core.data.source.remote.response.ResponseUser
import com.tbadhit.core.domain.model.User
import com.tbadhit.core.domain.repository.IUserRepository
import com.tbadhit.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IUserRepository {
    override fun getSearchUsers(query: String?): Flow<Resource<List<User>>> {
        return object : NetworkOnlyResource<List<User>, List<ResponseUser>>() {
            override fun loadFromNetwork(data: List<ResponseUser>): Flow<List<User>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseUser>>> {
                return remoteDataSource.getSearchUsers(query)
            }

        }.asFlow()
    }

    override fun getListFollower(username: String): Flow<Resource<List<User>>> {
        return object : NetworkOnlyResource<List<User>, List<ResponseUser>>() {
            override fun loadFromNetwork(data: List<ResponseUser>): Flow<List<User>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseUser>>> {
                return remoteDataSource.getListFollower(username)
            }

        }.asFlow()
    }

    override fun getListFollowing(username: String): Flow<Resource<List<User>>> {
        return object : NetworkOnlyResource<List<User>, List<ResponseUser>>() {
            override fun loadFromNetwork(data: List<ResponseUser>): Flow<List<User>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseUser>>> {
                return remoteDataSource.getListFollowing(username)
            }

        }.asFlow()
    }

    override fun getDetailUser(username: String): Flow<Resource<User>> {
        return object : NetworkOnlyResource<User, ResponseUser>() {
            override fun loadFromNetwork(data: ResponseUser): Flow<User> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<ResponseUser>> {
                return remoteDataSource.getDetailUser(username)
            }

        }.asFlow()
    }


    override fun getAllFavorites(): Flow<List<User>> {
        return localDataSource.getAllFavorites().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun check(id: Int): Flow<User>? {
        return localDataSource.check(id)?.map {
            DataMapper.mapEntityToDomain(it)
        }
    }


    override suspend fun insert(
        username: String?,
        id: Int?,
        avatarUrl: String?,
        isFavorite: Boolean?
    ) {
        val domainUser = DataMapper.mapDomainToEntity(username, id, avatarUrl, isFavorite)
        return localDataSource.insert(domainUser)
    }

    override suspend fun delete(user: User): Int {
        val domainUser =
            DataMapper.mapDomainToEntity(user.username, user.id, user.avatarUrl, user.isFavorite)
        return localDataSource.delete(domainUser)
    }

}