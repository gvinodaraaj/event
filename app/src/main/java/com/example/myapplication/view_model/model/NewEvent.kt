package com.example.myapplication.view_model.model

data class NewEvent(
    val name: String,
    val place: String,
    val startDate: String,
    val endDate: String,
    val amount: Double,
    val description:String,
    val isAlaram: Boolean=false,
    )
