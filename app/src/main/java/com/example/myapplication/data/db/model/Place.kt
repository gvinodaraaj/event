package com.example.myapplication.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Place(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "place_id") val id: Int = 0,
    @ColumnInfo(name = "place_name") val name: String,
    @ColumnInfo(name = "place_location") val location: String,
    )