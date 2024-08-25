package com.example.myapplication.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.model.ToDoCategory
import com.example.myapplication.data.db.model.User
import com.example.myapplication.data.db.repo.CategoryRepository
import com.example.myapplication.data.db.repo.UserRepository
import com.example.myapplication.view_model.model.NewToDoCategory

import kotlinx.coroutines.launch

class MainViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val _txtTitle = MutableLiveData<String>()
    val txtTitle: LiveData<String> get() = _txtTitle

    private val _isAlert = MutableLiveData<Boolean>()
    val isAlert: LiveData<Boolean> get() = _isAlert

    private var _liveDataMap = MutableLiveData<List<NewToDoCategory>>()
    val liveDataMap: LiveData<List<NewToDoCategory>> get() = _liveDataMap

    fun getLiveDataMap() {
       // _txtTitle.value =
    }
    fun setTxtTitle(txt: String) {
        _txtTitle.value = txt
    }

    fun setFilter(isAlert: Boolean) {
        _isAlert.value = isAlert
    }
   fun insert(category: ToDoCategory) = viewModelScope.launch {
       repository.insert(category)
        getAllCategory()
    }

    // Function to get all users
    fun getAllCategory() = viewModelScope.launch {
        val categories = repository.getAllCategorys()
        _liveDataMap.value = categories.map{NewToDoCategory(it.id,it.title,it.colour,it.assert,it.type)}
    }

}