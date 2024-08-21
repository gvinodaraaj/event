package com.example.myapplication.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "todo_id") val id: Int = 0,
    @ColumnInfo(name = "todo_title") val title: String,
    @ColumnInfo(name = "todo_place_id") val place: Int,
    @ColumnInfo(name = "todo_start_date") val startDate: String,
    @ColumnInfo(name = "todo_end_date") val endDate: String,
    @ColumnInfo(name = "todo_category_id") val category: Int,
    @ColumnInfo(name = "todo_user_id") val userId: Int,
    @ColumnInfo(name = "todo_assert_id") val assertId: Int,
    @ColumnInfo(name = "todo_amount") val amount: Double,
    @ColumnInfo(name = "todo_alarm") val alarm: Boolean,
    @ColumnInfo(name = "todo_alarm_id") val alarmId: Int,
)