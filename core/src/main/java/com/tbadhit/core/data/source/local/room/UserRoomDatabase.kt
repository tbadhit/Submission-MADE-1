package com.tbadhit.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tbadhit.core.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}