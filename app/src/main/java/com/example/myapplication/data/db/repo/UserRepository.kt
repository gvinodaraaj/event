package com.example.myapplication.data.db.repo

import android.provider.ContactsContract.CommonDataKinds.Phone
import com.example.myapplication.data.db.dao.UserDao
import com.example.myapplication.data.db.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAll()
    }

    suspend fun logedInUser(phone: String): User {
        return userDao.getLoginDetails(phone)
    }
    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    suspend fun isPhoneExist(phone:String):Boolean {
        return userDao.isPhoneNumberExists(phone)
    }
}