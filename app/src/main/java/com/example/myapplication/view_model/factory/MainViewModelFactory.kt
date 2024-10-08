package com.example.myapplication.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.db.repo.CategoryRepository
import com.example.myapplication.data.db.repo.EventRepository
import com.example.myapplication.data.db.repo.UserRepository
import com.example.myapplication.view_model.MainViewModel


class MainViewModelFactory(private val repository: CategoryRepository,private val repositoryEvent: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository,repositoryEvent) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}