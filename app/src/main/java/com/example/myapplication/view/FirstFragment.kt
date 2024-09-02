package com.example.myapplication.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MyApp
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.util.NewActivityEnum
import com.example.myapplication.view_model.MainViewModel
import com.example.myapplication.view_model.NewEventViewModel
import com.example.myapplication.view_model.factory.MainViewModelFactory
import com.example.myapplication.view_model.model.NewEvent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), MainActivity.CustomBackPressHandler, NewEventViewModel.Callback {

    private var _binding: FragmentFirstBinding? = null
    val formatDate = "MMM dd hh:mm a"
    private val viewModel: MainViewModel by activityViewModels() {
        MainViewModelFactory(
            (requireActivity().application as MyApp).repositoryCategory,
            (requireActivity().application as MyApp).repositoryEvent
        )
    }

    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeUi.setContent {
            AddEvent(
                TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                Modifier
                    .border(1.dp, Color.DarkGray, RectangleShape)
                    .fillMaxWidth()
                    .background(Color.White)
            ) { findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment) }
        }
    }

    @Composable
    fun AddEvent(
        txtBg: TextFieldColors, modifier: Modifier,
        categoryClick: () -> Unit
    ) {
        val Event by viewModel.Event.collectAsState()


        Column(
            Modifier.fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add Event",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(8.dp, 10.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextField(
                placeholder = {
                    Text(
                        text = "Enter Name",
                        color = Color.Gray,
                        fontSize = 14.sp,
                    )
                },
                value = Event.name,
                onValueChange = {
                    viewModel.updateEventDetails(name = it)
                },
                modifier = modifier,
                colors = txtBg,

                )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.DarkGray, RectangleShape)
                    .clickable { categoryClick() }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = Event.categoryName ?: "Select Category",
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(12.dp, 20.dp)
                    )
                    Image(
                        contentDescription = "category",
                        colorFilter = ColorFilter.tint(Color.Green),
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        modifier = Modifier
                            .size(40.dp)
                            .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                placeholder = {
                    Text(
                        text = "Enter Amount",
                        color = Color.Gray,
                        fontSize = 14.sp,
                    )
                },
                value = Event.amount.toString(),
                onValueChange = {
                    val parsedValue = it.toDoubleOrNull()
                    if (parsedValue != null || it.isEmpty()) {
                        viewModel.updateEventDetails(amount = parsedValue ?: 0.0)
                    }
                },
                modifier = modifier,
                colors = txtBg,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                placeholder = {
                    Text(
                        text = "Enter Place",
                        color = Color.Gray,
                        fontSize = 14.sp,
                    )
                },
                value = Event.place,
                onValueChange = {
                    viewModel.updateEventDetails(place = it)
                },
                modifier = modifier,
                colors = txtBg,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                placeholder = {
                    Text(
                        text = "Enter Description",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        maxLines = 3,
                        minLines = 3,
                    )
                },
                value = Event.description,
                onValueChange = {
                    viewModel.updateEventDetails(description = it)
                },
                modifier = modifier,
                colors = txtBg,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                StartDateAndTimePicker(Event)
                EndDateAndTimePicker(Event)
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                viewModel.newEvent(Event)
                findNavController().popBackStack()

            }) {
                Text(
                    text = "Add",
                    color = Color.White,
                    fontSize = 20.sp,
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun Preview() {
        AddEvent(
            TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            ),
            Modifier
                .border(1.dp, Color.DarkGray, RectangleShape)
                .fillMaxWidth()
                .background(Color.White),
        ) {}

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun handleBackPress(): Boolean {
        return true
    }

    @Composable
    fun EndDateAndTimePicker(Event: NewEvent) {
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        var Selected by remember { mutableStateOf<Long>(Event.endDate) }
        val min = Event.startDate

        val timePickerDialog = TimePickerDialog(
            context,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = Selected
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)
                Selected = calendar.timeInMillis
                viewModel.updateEventDetails(endDate = Selected)
            }, hour, minute, false
        )

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                val calendarTemp = Calendar.getInstance()
                calendarTemp.timeInMillis = min
                Selected = calendar.timeInMillis
                timePickerDialog.show()
            }, year, month, day
        )

        Text(
            text = "${Event.endDate?.toString()?.Date() ?: "Select Date"}",
            color = Color.Black,
            fontSize = 15.sp,
            modifier = Modifier
                .border(1.dp, Color.DarkGray, RectangleShape)
                .padding(8.dp, 12.dp)
                .clickable {
                    if (min > 0) {
                        datePickerDialog.datePicker.minDate = min
                        calendar.timeInMillis = min
                        val yearSet = calendar.get(Calendar.YEAR)
                        val monthSet = calendar.get(Calendar.MONTH)
                        val daySet = calendar.get(Calendar.DAY_OF_MONTH)
                        datePickerDialog.datePicker.updateDate(yearSet, monthSet, daySet)
                    }
                    datePickerDialog.show()
                },
            textAlign = TextAlign.Center
        )
    }

    @Composable
    fun StartDateAndTimePicker(Event: NewEvent) {
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        var Selected by remember { mutableStateOf<Long>(Event.startDate) }
        val min = viewModel.txtStart.value?.toLong() ?: 0

        val timePickerDialog = TimePickerDialog(
            context,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = Selected
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)
                Selected = calendar.timeInMillis
                viewModel.setTxtStart(Selected)
                viewModel.updateEventDetails(startDate = Selected)
                if (Selected > min) {
                    viewModel.updateEventDetails(endDate = 0)
                }
            }, hour, minute, false
        )

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                Selected = calendar.timeInMillis
                timePickerDialog.show()
            }, year, month, day
        )

        Text(
            text = "${Event.startDate?.toString()?.Date() ?: "End Date"}",
            color = Color.Black,
            fontSize = 15.sp,
            modifier = Modifier
                .border(1.dp, Color.DarkGray, RectangleShape)
                .padding(8.dp, 12.dp)
                .clickable {
                    if (min > 0) {
                        val calendarTemp = Calendar.getInstance()
                        calendarTemp.timeInMillis = min
                        val yearSet = calendarTemp.get(Calendar.YEAR)
                        val monthSet = calendarTemp.get(Calendar.MONTH)
                        val daySet = calendarTemp.get(Calendar.DAY_OF_MONTH)
                        datePickerDialog.datePicker.updateDate(yearSet, monthSet, daySet)
                    }
                    datePickerDialog.show()
                },
            textAlign = TextAlign.Center
        )
    }

    override fun onFunctionTriggered(result: NewActivityEnum) {
        TODO("Not yet implemented")
    }

    fun String.Date(): String {
        if (this.toLong() < 1) {
            return "Select Date"
        } else {
            val date = Date(this.toLong()) // Convert to Date object
            val formatter =
                SimpleDateFormat(formatDate, Locale.getDefault()) // Formatter for "MMM dd"
            return formatter.format(date)
        }
    }
}