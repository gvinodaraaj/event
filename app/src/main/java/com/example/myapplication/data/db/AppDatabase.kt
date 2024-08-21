package com.example.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.db.dao.EventDao
import com.example.myapplication.data.db.dao.UserDao
import com.example.myapplication.data.db.model.Event
import com.example.myapplication.data.db.model.User

@Database(entities = [User::class,Event::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}