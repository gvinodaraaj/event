package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.repo.UserRepository
import dagger.hilt.android.HiltAndroidApp


class MyApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { UserRepository(database.userDao()) }
}