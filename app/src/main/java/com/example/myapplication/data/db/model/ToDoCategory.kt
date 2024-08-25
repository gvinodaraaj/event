package com.example.myapplication.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoCategory(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "todo_category_id") val id: Int = 0,
    @ColumnInfo(name = "todo_category_title") val title: String,
    @ColumnInfo(name = "todo_category_colour") val colour: String,
    @ColumnInfo(name = "todo_category_type") val type: Boolean,
    @ColumnInfo(name = "todo_category_assert") val assert: String,
)