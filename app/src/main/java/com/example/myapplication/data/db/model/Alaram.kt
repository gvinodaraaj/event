package com.example.myapplication.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alaram(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "alarm_id") val id: Int = 0,
    @ColumnInfo(name = "alarm_name") val name: String,
    @ColumnInfo(name = "alarm_assert") val assert: String,
    @ColumnInfo(name = "alarm_colour") val colour: String,
    @ColumnInfo(name = "alarm_time") val priority: Int=0,
    @ColumnInfo(name = "alarm_type") val type: Boolean = true,
    )