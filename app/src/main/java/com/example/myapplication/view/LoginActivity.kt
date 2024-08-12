package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MyApp
import com.example.myapplication.databinding.LoginMainBinding
import com.example.myapplication.view_model.ListActivityViewModel
import com.example.myapplication.view_model.LoginViewModel
import com.example.myapplication.view_model.factory.ListActivityViewModelFactory
import com.example.myapplication.view_model.factory.LoginViewModelFactory

class LoginActivity : AppCompatActivity(), LoginViewModel.Callback {
    private lateinit var binding: LoginMainBinding
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();
        binding.user = viewModel
        viewModel.setCallback(this)
    }

    override fun onFunctionTriggered() {
        val intent = Intent(this, ListActivity::class.java)
        intent.putExtra("FromLogin", true)
        startActivity(intent)
        finish()
    }
}