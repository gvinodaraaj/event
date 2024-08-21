package com.example.myapplication.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "category_id") val id: Int = 0,
    @ColumnInfo(name = "category_name") val name: String,
    @ColumnInfo(name = "category_assert") val assert: String,
    @ColumnInfo(name = "category_colour") val colour: String,
    @ColumnInfo(name = "category_priority") val priority: Int,
    @ColumnInfo(name = "category_type") val type: Boolean,
    )