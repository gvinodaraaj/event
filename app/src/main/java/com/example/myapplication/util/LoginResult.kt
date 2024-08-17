package com.example.myapplication.util

import com.example.myapplication.data.db.model.User
import com.example.myapplication.view_model.model.NewAccount

sealed class LoginResult {
    data class Success(val User: NewAccount) : LoginResult()
    data class Error(val message: String) : LoginResult()
    data class Password(val phone: String) : LoginResult()

}
