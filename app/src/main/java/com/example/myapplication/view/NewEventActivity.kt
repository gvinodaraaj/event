package com.example.myapplication.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MyApp
import com.example.myapplication.databinding.ActivityNewTodoBinding
import com.example.myapplication.util.NewActivityEnum
import com.example.myapplication.view_model.NewEventViewModel
import com.example.myapplication.view_model.factory.NewEventViewModelFactory
import com.example.myapplication.view_model.model.NewEvent
import java.util.Calendar

class NewEventActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, NewEventViewModel.Callback {
    private var day = 0
    private var month: Int = 0
    private var year: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0
    private var myDay = 0
    private var myMonth: Int = 0
    private var myYear: Int = 0
    private var myHour: Int = 0
    private var myMinute: Int = 0
    var isNew: Boolean = false
    lateinit var dateEditText: EditText
    private lateinit var binding: ActivityNewTodoBinding
    private val viewModel: NewEventViewModel by viewModels {
        NewEventViewModelFactory((application as MyApp).repositoryEvent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();
        binding.account = viewModel
        viewModel.setCallback(this)
        binding.eTStartDate.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            dateEditText=binding.eTStartDate
            val datePickerDialog =
                DatePickerDialog(this@NewEventActivity, this@NewEventActivity, year, month,day)
            datePickerDialog.show()
        }

        binding.eTEndDate.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            dateEditText = binding.eTEndDate
            val datePickerDialog =
                DatePickerDialog(this@NewEventActivity, this@NewEventActivity, year, month, day)
            datePickerDialog.show()
        }
        binding.btnEvent.setOnClickListener {
            val event = NewEvent(
                binding.eTName.text.toString().trim(),
                binding.eTPlace.text.toString().trim(),
                binding.eTStartDate.text.toString().trim(),
                binding.eTEndDate.text.toString().trim(),
                binding.eTAmount.text.toString().trim().toDouble(),
                binding.eTDescription.text.toString().trim(),
                binding.isComplete.isChecked
            )
            viewModel.newEvent(event)
        }
    }

    override fun onFunctionTriggered(result: NewActivityEnum) {
        when (result) {
            NewActivityEnum.Mandatory -> {
                Toast.makeText(this, "Enter All Mandatory", Toast.LENGTH_SHORT).show()
            }

            NewActivityEnum.Sucess -> {
                finish()
            }

            else -> {}
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month + 1
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            this@NewEventActivity, this@NewEventActivity, hour, minute,
            false
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute

        dateEditText.setText("" + myYear + "/" + myMonth + "/" + myDay + "  " + myHour + ":" + myMinute)
    }
}