package com.tbadhit.core.data.source.local.room

import androidx.room.*
import com.tbadhit.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity): Int

    @Query("SELECT * FROM users WHERE id = :id")
    fun check(id: Int): Flow<UserEntity>?

    @Query("SELECT * from users")
    fun getAllFavorites(): Flow<List<UserEntity>>

}