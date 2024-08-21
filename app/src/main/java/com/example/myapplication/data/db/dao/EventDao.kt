package com.example.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.db.model.Event
import com.example.myapplication.data.db.model.User

@Dao
interface EventDao {
    @Query("SELECT * FROM Event")
    suspend fun getAll(): MutableList<Event>

    @Query("SELECT * FROM Event WHERE event_user_id IN (:eventUserId)")
    suspend fun getUsersEvent(eventUserId: Int): MutableList<Event>

    @Update
    suspend fun update(event: Event)

    @Insert
    suspend fun insert(event: Event)

    @Delete
    suspend fun delete(event: Event)
}