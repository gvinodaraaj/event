package com.example.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.db.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): MutableList<User>

    @Query("SELECT COUNT(*) > 0 FROM user WHERE user_phone = :phoneNumber LIMIT 1")
    suspend fun isPhoneNumberExists(phoneNumber: String): Boolean

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
     suspend fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE user_phone IN (:userPhone)")
    suspend fun getLoginDetails(userPhone: String): User

    @Insert
    suspend fun insertAll(vararg users: User)

    @Insert
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)
}