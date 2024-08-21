package com.example.myapplication.view_model

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.model.User
import com.example.myapplication.data.db.repo.UserRepository
import com.example.myapplication.util.NewActivityEnum
import com.example.myapplication.view_model.model.NewAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class NewAccountViewModel(private val repository: UserRepository) : ViewModel() {
    private var _acc = MutableLiveData<NewAccount>()
    val acc: LiveData<NewAccount> get() = _acc

    interface Callback {
        fun onFunctionTriggered(result: NewActivityEnum)
    }

    private var callback: Callback? = null
    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun insert(user: User) = viewModelScope.launch {

        val exists: Boolean = withContext(Dispatchers.IO) {
            isExist(user.phone)
        }.await()

        // If the user does not exist, insert them into the database
        if (!exists) {
            withContext(Dispatchers.IO) {
                repository.insert(user)
            }
            functionToBeCalled(NewActivityEnum.Sucess)
        }
        else{
            functionToBeCalled(NewActivityEnum.PhoneExist)
        }


    }

    fun functionToBeCalled(result: NewActivityEnum) {
        // Check if callback is not null before calling it
        callback?.onFunctionTriggered(result)
    }

    fun isExist(input: String) = viewModelScope.async  {
        return@async repository.isPhoneExist(input)
    }

    fun newAccount(newAccount: NewAccount) {
        if (newAccount.name.isBlank() || newAccount.phone.isBlank() || newAccount.mail.isBlank() || newAccount.password.isBlank() || newAccount.rePassword.isBlank() ) {
            functionToBeCalled(NewActivityEnum.Mandatory)
        }
        else{
            if (newAccount.password.equals(newAccount.rePassword) ) {
                insert(
                    User(
                        name = newAccount.name,
                        password = newAccount.password,
                        phone = newAccount.phone,
                        mail = newAccount.mail,
                    )
                )

            } else {
                functionToBeCalled(NewActivityEnum.PasswordError)
            }
        }
    }
    // This method will be called from the Activity to trigger the function
    /*val userLiveData = MutableLiveData(User(
        name = "vinod",
        password = "3123",
        phone = "9600104721",
        mail = "g.vinodaraaj@gmail.com"
    ))*/
}
