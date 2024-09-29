package com.abhisek.project.bookshelf.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhisek.project.bookshelf.data.local.entity.UserEntity

@Dao
interface BookShelfDao {

    @Query("SELECT * FROM users WHERE mailId = :mailId")
    suspend fun getUserByMailId(mailId: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>

}