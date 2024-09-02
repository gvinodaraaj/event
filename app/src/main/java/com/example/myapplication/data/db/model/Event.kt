package com.example.myapplication.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "event_id") val id: Int = 0,
    @ColumnInfo(name = "event_title") val title: String="",
    @ColumnInfo(name = "event_place") val place: String="",
    @ColumnInfo(name = "event_start_date") val startDate: Long=0,
    @ColumnInfo(name = "event_end_date") val endDate: Long=0,
    @ColumnInfo(name = "event_category_id") val category: Int=0,
    @ColumnInfo(name = "event_user_id") val userId: Int=0,
    @ColumnInfo(name = "event_assert_id") val assertId: Int=0,
    @ColumnInfo(name = "event_amount") val amount: Double=0.0,
    @ColumnInfo(name = "event_alarm") val alarm: Boolean=false,
    @ColumnInfo(name = "event_alarm_id") val alarmId: Int=0,
    @ColumnInfo(name = "event_priority") val priority: Int=0,
    @ColumnInfo(name = "event_base_id") val baseId: Int=0,
    @ColumnInfo(name = "event_level") val level: Int=0,
)