package com.example.myapplication.data.db.repo

import com.example.myapplication.data.db.dao.EventDao
import com.example.myapplication.data.db.model.Event

class EventRepository(private val eventDao: EventDao) {
    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    suspend fun getAllEvents(): List<Event> {
        return eventDao.getAll()
    }


    suspend fun delete(event: Event) {
        eventDao.delete(event)
    }

}