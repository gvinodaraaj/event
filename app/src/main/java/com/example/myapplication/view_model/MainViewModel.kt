package com.example.myapplication.view_model

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.model.Event
import com.example.myapplication.data.db.model.ToDoCategory
import com.example.myapplication.data.db.model.User
import com.example.myapplication.data.db.repo.CategoryRepository
import com.example.myapplication.data.db.repo.EventRepository
import com.example.myapplication.data.db.repo.UserRepository
import com.example.myapplication.util.NewActivityEnum
import com.example.myapplication.view_model.model.NewEvent
import com.example.myapplication.view_model.model.NewToDoCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch
import java.util.Date

class MainViewModel(
    private val repository: CategoryRepository,
    private val repositoryEvent: EventRepository
) : ViewModel() {

    private val _txtTitle = MutableLiveData<String>()
    val txtTitle: LiveData<String> get() = _txtTitle

    private val _isAlert = MutableLiveData<Boolean>()
    val isAlert: LiveData<Boolean> get() = _isAlert

    private var _liveDataMap = MutableLiveData<List<NewToDoCategory>>()
    val liveDataMap: LiveData<List<NewToDoCategory>> get() = _liveDataMap


    private var _Event = MutableStateFlow<NewEvent>(NewEvent(amount = 0.0, startDate = 0, description = "test description", place = "home", isAlaram = true, endDate = 0, name = "vinodaraaj", categoryId = 0, categoryName = ""))

    val Event: StateFlow<NewEvent> get() = _Event

    private val _txtStart = MutableLiveData<Long>()
    val txtStart: LiveData<Long> get() = _txtStart

    private val _txtEnd = MutableLiveData<Long>()
    val txtEnd: LiveData<Long> get() = _txtEnd

    interface Callback {
        fun onFunctionTriggered(result: NewActivityEnum)
    }

    private var callback: Callback? = null
    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun functionToBeCalled(result: NewActivityEnum) {
        // Check if callback is not null before calling it
        callback?.onFunctionTriggered(result)
    }

    fun setTxtTitle(txt: String) {
        _txtTitle.value = txt
    }

    fun setTxtStart(txt: Long) {
        _txtStart.value = txt
    }

    fun setTxtEnd(txt: Long) {
        _txtEnd.value = txt
    }


    fun setFilter(isAlert: Boolean) {
        _isAlert.value = isAlert
    }

    fun insert(category: ToDoCategory) = viewModelScope.launch {
        repository.insert(category)
        getAllCategory()
    }

    fun insertEvent(event: Event) = viewModelScope.launch {
        repositoryEvent.insert(event)
    }

    fun deletCategory(category: ToDoCategory) = viewModelScope.launch {
        repository.delete(category)
        getAllCategory()
    }

    fun newEvent(event: NewEvent) {
        if (event.name.isBlank() || event.place.isBlank()) {
            functionToBeCalled(NewActivityEnum.Mandatory)
        } else {
            insertEvent(
                Event(
                    title = event.name,
                    place = event.place,
                    startDate = event.startDate,
                    endDate = event.endDate,
                    amount = event.amount,
                    category = event.categoryId,
                    alarm = event.isAlaram
                )
            )
            functionToBeCalled(NewActivityEnum.Sucess)
        }
    }

    fun updateEvent(newEvent: NewEvent) {
        _Event.value = newEvent
    }

    // Alternatively, update specific fields in the event
    fun updateEventDetails(
        amount: Double? = null,
        startDate: Long? = null,
        description: String? = null,
        place: String? = null,
        isAlaram: Boolean? = null,
        endDate: Long? = null,
        name: String? = null,
        categoryId: Int? = null,
        categoryName: String? = null
    ) {
        _Event.value = _Event.value.copy(
            amount = amount ?: _Event.value.amount,
            startDate = startDate ?: _Event.value.startDate,
            description = description ?: _Event.value.description,
            place = place ?: _Event.value.place,
            isAlaram = isAlaram ?: _Event.value.isAlaram,
            endDate = endDate ?: _Event.value.endDate,
            name = name ?: _Event.value.name,
            categoryId = categoryId ?: _Event.value.categoryId,
            categoryName = categoryName ?: _Event.value.categoryName
        )
    }
    // Function to get all users
    fun getAllCategory() = viewModelScope.launch {
        val categories = repository.getAllCategorys()
        _liveDataMap.value =
            categories.map { NewToDoCategory(it.id, it.title, it.colour, it.assert, it.type) }
    }

}