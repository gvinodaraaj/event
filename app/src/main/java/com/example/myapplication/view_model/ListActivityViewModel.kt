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
import com.example.myapplication.data.db.repo.UserRepository
import kotlinx.coroutines.launch

class ListActivityViewModel(private val repository: UserRepository) : ViewModel() {
    private var _items = MutableLiveData<List<User>>()
    val items: LiveData<List<User>> get() = _items

    // Function to insert a user
    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
        getAllUsers()
    }

    // Function to get all users
    fun getAllUsers() = viewModelScope.launch {
        val user = repository.getAllUsers()
        _items.value = user
    }

    // Function to delete all users
    fun deleteAll(user: User) = viewModelScope.launch {
        repository.delete(user)
    }
    fun addItem() {
        val itemList = listOf(
            User(
                name = "vinod",
                password = "3123",
                phone = "9600104721",
                mail = "g.vinodaraaj@gmail.com"
            ),
            User(
                name = "Raaj",
                password = "3123",
                phone = "8072865178",
                mail = "g.vinodaraaj@gmail.com"
            ),
            User(
                name = "vinod",
                password = "3123",
                phone = "7397394997",
                mail = "g.vinodaraaj@gmail.com"
            )
        )
        val currentList = _items.value ?: mutableListOf()
        val updatedList = currentList.toMutableList().apply { addAll(itemList) }
        _items.value = updatedList
    }

}
