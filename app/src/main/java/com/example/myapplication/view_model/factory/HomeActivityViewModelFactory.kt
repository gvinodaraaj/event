package com.example.myapplication.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.db.repo.EventRepository
import com.example.myapplication.data.db.repo.UserRepository
import com.example.myapplication.view_model.HomeActivityViewModel
import com.example.myapplication.view_model.ListActivityViewModel


class HomeActivityViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}