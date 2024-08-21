package com.example.myapplication.view_model

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.myapplication.data.db.model.Event
import com.example.myapplication.data.db.model.User
import com.example.myapplication.data.db.repo.EventRepository
import com.example.myapplication.data.db.repo.UserRepository
import com.example.myapplication.util.NewActivityEnum
import com.example.myapplication.view_model.model.NewAccount
import com.example.myapplication.view_model.model.NewEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NewEventViewModel(private val repository: EventRepository) : ViewModel() {
    private var _event = MutableLiveData<NewEvent>()
    val event: LiveData<NewEvent> get() = _event

    interface Callback {
        fun onFunctionTriggered(result: NewActivityEnum)
    }

    private var callback: Callback? = null
    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun insert(event: Event) = viewModelScope.launch {
        repository.insert(event)
    }

    fun functionToBeCalled(result: NewActivityEnum) {
        // Check if callback is not null before calling it
        callback?.onFunctionTriggered(result)
    }


    fun newEvent(event: NewEvent) {
        if (event.name.isBlank() || event.place.isBlank() || event.startDate.isBlank()  ) {
            functionToBeCalled(NewActivityEnum.Mandatory)
        }
        else{
                insert(
                    Event(
                        title = event.name,
                        place = event.place,
                        startDate = event.startDate,
                        endDate = event.endDate,
                        amount = event.amount,
                        alarm = event.isAlaram
                    )
                )
                functionToBeCalled(NewActivityEnum.Sucess)
        }
    }

}
