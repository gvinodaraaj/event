package com.example.myapplication.data.db.repo

import com.example.myapplication.data.db.dao.CategoryDao
import com.example.myapplication.data.db.model.ToDoCategory

class CategoryRepository(private val categoryDao: CategoryDao) {
    suspend fun insert(category: ToDoCategory) {
        categoryDao.insert(category)
    }

    suspend fun getAllCategorys(): List<ToDoCategory> {
        return categoryDao.getAll()
    }


    suspend fun delete(category: ToDoCategory) {
        categoryDao.delete(category)
    }

}