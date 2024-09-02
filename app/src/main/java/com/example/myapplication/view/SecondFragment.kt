package com.example.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.view_model.MainViewModel
import com.example.myapplication.view_model.factory.MainViewModelFactory
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MyApp
import com.example.myapplication.R
import com.example.myapplication.data.db.model.ToDoCategory
import com.example.myapplication.view_model.model.NewToDoCategory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), MainActivity.CustomBackPressHandler {

    private var _binding: FragmentSecondBinding? = null
    private val viewModel: MainViewModel by activityViewModels() {
        MainViewModelFactory((requireActivity().application as MyApp).repositoryCategory,(requireActivity().application as MyApp).repositoryEvent)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeUi.setContent {
            val itemList by viewModel.liveDataMap.observeAsState(initial = emptyList())

            MyAlertDialog(
                Modifier
                    .border(1.dp, Color.DarkGray, RectangleShape),
                TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            AddEvent(itemList) {
                viewModel.setFilter(true)
            }
        }
    }

    @Composable
    fun AddEvent(
        itemsList: List<NewToDoCategory>,
        categoryClick: () -> Unit
    ) {

        Column(
            Modifier.fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Category",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(8.dp, 10.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            if (itemsList.size >= 1) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsList.map { CatogeryListItem(it) }
                }
            } else {
                Text(
                    text = "Add Category",
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(8.dp, 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { categoryClick() }) {
                Text(
                    text = "Add",
                    color = Color.White,
                    fontSize = 20.sp,
                )
            }
        }
    }


    @Composable
    fun CatogeryListItem(itemsList: NewToDoCategory) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.updateEventDetails(categoryName = itemsList.name, categoryId = itemsList.id)
                   findNavController().popBackStack()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            Text(
                text = itemsList.name,
                modifier = Modifier
                    .padding(4.dp,10.dp)
            )
            Image(
                contentDescription = "category",
                colorFilter = ColorFilter.tint(Color.Green),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .size(40.dp)
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .clickable {
                        viewModel.deletCategory(ToDoCategory(itemsList.id,itemsList.name,itemsList.colour,itemsList.type,itemsList.assert))
                    }
            )
        }
    }

    @Composable
    fun MyAlertDialog(txtModifier: Modifier, txtBg: TextFieldColors) {
        val TxtTitle by viewModel.txtTitle.observeAsState(initial = "")
        val isFilter by viewModel.isAlert.observeAsState(initial = false)
        var isChecked by remember { mutableStateOf(false) }

        if (isFilter) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.setFilter(false)
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        TextField(
                            label = {
                                Text(
                                    text = "Enter Category",
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                )
                            },
                            value = TxtTitle,
                            onValueChange = { newText ->
                                viewModel.setTxtTitle(newText)
                            },
                            singleLine = true,
                            modifier = txtModifier,
                            colors = txtBg,
                        )
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { isChecked = it }
                        )
                    }
                },
                confirmButton = { // 6
                    Button(
                        onClick = {
                            val TxtTitle = viewModel.txtTitle.value.toString()
                            val newCategory = ToDoCategory(
                                title = TxtTitle,
                                assert = "",
                                colour = "#008000",
                                type = isChecked
                            )
                            viewModel.insert(newCategory)
                            viewModel.setFilter(false)
                        }
                    ) {
                        Text(
                            text = "Add",
                            color = Color.White
                        )
                    }
                }
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun Preview() {
        // AddEvent() {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun handleBackPress(): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllCategory()
    }

}