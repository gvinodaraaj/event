package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
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
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.view.MainActivity
import com.example.myapplication.view_model.LoginViewModel
import com.example.myapplication.view_model.MainViewModel
import com.example.myapplication.view_model.factory.LoginViewModelFactory
import com.example.myapplication.view_model.factory.MainViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() , MainActivity.CustomBackPressHandler {

    private var _binding: FragmentFirstBinding? = null
/*    private val viewModel: MainViewModel by activityViewModels() {
        MainViewModelFactory((requireActivity().application as MyApp).repositoryCategory)
    }*/
    // This property is only valid between onCreateView and
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
                    .background(Color.White),
                Modifier
                    .border(1.dp, Color.DarkGray, RectangleShape)
                    .padding(8.dp, 10.dp)
            ) { findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment) }
        }
    }

    @Composable
    fun AddEvent(
        txtBg: TextFieldColors, txtModifier: Modifier, txtDateModifier: Modifier,
        categoryClick: () -> Unit
    ) {
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
            TextField(
                placeholder = {
                    Text(
                        text = "Enter Name",
                        color = Color.Gray,
                        fontSize = 14.sp,
                    )
                },
                value = "",
                onValueChange = {},
                modifier = txtModifier,
                colors = txtBg,

                )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.DarkGray, RectangleShape).clickable { categoryClick() }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Select Category",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(8.dp, 16.dp)
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
                value = "",
                onValueChange = {},
                modifier = txtModifier,
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
                value = "", onValueChange = {},
                modifier = txtModifier,
                colors = txtBg,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                placeholder = {
                    Text(
                        text = "Enter Descreption",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        maxLines = 3,
                        minLines = 3,
                    )
                },
                value = "",
                onValueChange = {},
                modifier = txtModifier,
                colors = txtBg,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {

                Text(
                    text = "Start Date",
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = txtDateModifier,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "End Date",
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = txtDateModifier,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {}) {
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
            Modifier
                .border(1.dp, Color.DarkGray, RectangleShape)
                .padding(8.dp, 10.dp)
        ) {}

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun handleBackPress(): Boolean {
        return true
    }
}