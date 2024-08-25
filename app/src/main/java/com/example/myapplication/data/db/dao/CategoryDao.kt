package com.example.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.db.model.ToDoCategory

@Dao
interface CategoryDao {
    @Query("SELECT * FROM ToDoCategory")
    suspend fun getAll(): MutableList<ToDoCategory>

    @Query("SELECT * FROM ToDoCategory WHERE todo_category_id IN (:id)")
    suspend fun getCategoryDetails(id: String): ToDoCategory

    @Query("SELECT * FROM ToDoCategory WHERE todo_category_title IN (:name)")
    suspend fun searchCategoryDetails(name: String): List<ToDoCategory>


    @Insert
    suspend fun insert(toDoCategory: ToDoCategory)

    @Delete
    suspend fun delete(toDoCategory: ToDoCategory)
}