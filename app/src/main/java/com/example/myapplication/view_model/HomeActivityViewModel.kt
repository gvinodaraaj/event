package com.example.myapplication.view_model

import com.example.myapplication.data.db.model.User

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.db.model.Event
import com.example.myapplication.data.db.repo.EventRepository
import com.example.myapplication.data.db.repo.UserRepository
import kotlinx.coroutines.launch

class HomeActivityViewModel(private val repository: EventRepository) : ViewModel() {
    private var _items = MutableLiveData<List<Event>>()
    val items: LiveData<List<Event>> get() = _items

    // Function to insert a user
    fun insert(event: Event) = viewModelScope.launch {
        repository.insert(event)
        getAllUsers()
    }

    // Function to get all users
    fun getAllUsers() = viewModelScope.launch {
        val user = repository.getAllEvents()
        _items.value = user
    }

    // Function to delete all users
    fun deletEvent(event: Event) = viewModelScope.launch {
        repository.delete(event)
    }

}
