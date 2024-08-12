package com.example.myapplication.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.db.repo.UserRepository
import com.example.myapplication.view_model.ListActivityViewModel
import com.example.myapplication.view_model.LoginViewModel
import com.example.myapplication.view_model.NewAccountViewModel


class NewAccountViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewAccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewAccountViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}