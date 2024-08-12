package com.example.myapplication.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "user_name") val name: String,
    @ColumnInfo(name = "user_password") val password: String,
    @ColumnInfo(name = "user_mail") val mail: String,
    @ColumnInfo(name = "user_phone") val phone: String,
    )