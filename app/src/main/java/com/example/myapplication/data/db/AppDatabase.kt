package com.example.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.data.db.dao.CategoryDao
import com.example.myapplication.data.db.dao.EventDao
import com.example.myapplication.data.db.dao.UserDao
import com.example.myapplication.data.db.model.Alaram
import com.example.myapplication.data.db.model.Event
import com.example.myapplication.data.db.model.ToDo
import com.example.myapplication.data.db.model.ToDoCategory
import com.example.myapplication.data.db.model.User
import com.example.myapplication.data.db.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class, Event::class,ToDoCategory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
   abstract fun categoryDao(): CategoryDao

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

        private class AppDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                // Insert the default rows here
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
            //          populateDatabase(database.categoryDao())
                    }
                }
            }

            suspend fun populateDatabase(categoryDao: CategoryDao) {
          //      val income = ToDoCategory(title = "Income", assert = "", colour = "#008000", type = true)
           //   val expense = ToDoCategory(title = "Expense", assert ="", colour = "#FF0000", type = false)
          //     categoryDao.insert(income)
           //   categoryDao.insert(expense)

            }
        }
    }
}