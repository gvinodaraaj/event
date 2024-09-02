package com.example.myapplication.view_model.model

import java.util.Date

data class NewEvent(
    val name: String,
    val place: String,
    val startDate: Long,
    val endDate: Long,
    val amount: Double,
    val description:String,
    val categoryName:String,
    val categoryId:Int,
    val isAlaram: Boolean=false,
    )
