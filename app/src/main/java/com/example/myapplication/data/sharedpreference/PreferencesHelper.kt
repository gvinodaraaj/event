package com.example.myapplication.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.view_model.model.NewAccount

class PreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs_login", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun saveLogin(user: NewAccount){
        saveString("UserName",user.name)
        saveString("UserPhone",user.phone)
        saveString("UserMail",user.mail)
        saveString("UserPassword",user.password)
        saveString("UserId",user.rePassword)
    }

    fun getLogin():NewAccount{
       val UserName= getString("UserName","")+""
        val UserPhone= getString("UserPhone","")+""
        val UserMail=getString("UserMail","")+""
        val UserPassword=getString("UserPassword","")+""
        val UserId= getString("UserId","")+""
        return NewAccount(UserName,UserPassword,UserId,UserMail,UserPhone)
    }

    // Add more methods for other data types like Int, Boolean, etc.
}
