package com.example.myapplication.view_model

import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.model.User
import com.example.myapplication.data.db.repo.UserRepository
import com.example.myapplication.util.LoginResult
import com.example.myapplication.view_model.model.NewAccount
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private var _acc = MutableLiveData<User>()
    private var _pass = MutableLiveData<NewAccount>()
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

    fun isValid(userName: String, password: String): Boolean {
        return userName.isNotEmpty() && password.isNotEmpty()
    }

    //check user
    suspend fun isLogin(phone: String, password: String): LoginResult {
        val result: LoginResult
        val user = repository.logedInUser(phone) as? User
        if (user != null && user.password == password) {
            result = LoginResult.Success(
                NewAccount(
                    user.name,
                    user.password,
                    user.password,
                    user.mail,
                    user.phone
                )
            )
        } else {
            result = LoginResult.Error("Credentials not matching")
        }

        return result
    }

    suspend fun getPassword(phone: String): LoginResult {
        val result: LoginResult
        val user = repository.logedInUser(phone) as? User
        if (user != null  ) {
            result = LoginResult.Password(user.password)
        } else {
            result = LoginResult.Error("User not Registered")
        }

        return result
    }


    // This method will be called from the Activity to trigger the function
    val userLiveData = MutableLiveData(
        User(
            name = "vinod",
            password = "3123312",
            phone = "9600104721",
            mail = "g.vinodaraaj@gmail.com"
        )
    )


}
