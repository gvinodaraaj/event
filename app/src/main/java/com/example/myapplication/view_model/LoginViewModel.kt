package com.example.myapplication.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.model.User
import com.example.myapplication.data.db.repo.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {
    private var _acc = MutableLiveData<User>()
    val acc: LiveData<User> get() = _acc
    interface Callback {
        fun onFunctionTriggered()
    }

    private var callback: Callback? = null

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun functionToBeCalled() {
        // Check if callback is not null before calling it
        callback?.onFunctionTriggered()
    }
    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
        functionToBeCalled()
    }
    // This method will be called from the Activity to trigger the function
    val userLiveData = MutableLiveData(User(
        name = "vinod",
        password = "3123",
        phone = "9600104721",
        mail = "g.vinodaraaj@gmail.com"
    ))


}
