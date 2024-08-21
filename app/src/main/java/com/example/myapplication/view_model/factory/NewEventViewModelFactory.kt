package com.example.myapplication.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.db.repo.EventRepository
import com.example.myapplication.data.db.repo.UserRepository
import com.example.myapplication.view_model.ListActivityViewModel
import com.example.myapplication.view_model.LoginViewModel
import com.example.myapplication.view_model.NewAccountViewModel
import com.example.myapplication.view_model.NewEventViewModel


class NewEventViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewEventViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}