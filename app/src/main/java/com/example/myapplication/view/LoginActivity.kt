package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MyApp
import com.example.myapplication.data.sharedpreference.PreferencesHelper
import com.example.myapplication.databinding.LoginMainBinding
import com.example.myapplication.util.LoginResult
import com.example.myapplication.view_model.ListActivityViewModel
import com.example.myapplication.view_model.LoginViewModel
import com.example.myapplication.view_model.factory.ListActivityViewModelFactory
import com.example.myapplication.view_model.factory.LoginViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
        binding.txtForget.setOnClickListener {
            val userName = binding.editNameText.text.toString()
            if (userName.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val userDeferred = async(Dispatchers.IO) {
                            viewModel.getPassword(userName)
                        }
                        val userData = userDeferred.await()
                        loginFun(userData)
                    } catch (e: Exception) {
                        // Handle any errors that might occur
                        e.printStackTrace()
                        loginFun(LoginResult.Error("Failed to fetch user data"))
                    }
                }
            } else {
                Toast.makeText(
                    this.applicationContext,
                    "Enter user name",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.sugnUpButton.setOnClickListener {
            val intent = Intent(this, NewAccountActivity::class.java)
            intent.putExtra("FromLogin", true)
            startActivity(intent)
        }
        viewModel.setCallback(this)
    }

    override fun onFunctionTriggered() {
        val userName = binding.editNameText.text.toString()
        val password = binding.editTextPassword.text.toString()
        if (viewModel.isValid(userName, password)) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val userDeferred = async(Dispatchers.IO) {
                        viewModel.isLogin(userName, password)
                    }
                    val userData = userDeferred.await()
                    loginFun(userData)
                } catch (e: Exception) {
                    // Handle any errors that might occur
                    e.printStackTrace()
                    loginFun(LoginResult.Error("Failed to fetch user data"))
                }
            }
        } else {
            Toast.makeText(
                this.applicationContext,
                "Enter user name and password",
                Toast.LENGTH_SHORT
            ).show()

        }

    }

    fun loginFun(result: LoginResult) {
        when (result) {
            is LoginResult.Error -> {
                Toast.makeText(
                    this.applicationContext,
                    (result as LoginResult.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is LoginResult.Success -> {
                val user= result as LoginResult.Success
                PreferencesHelper(this).saveLogin(user.User)
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("FromLogin", true)
                startActivity(intent)
                finish()
            }

            is LoginResult.Password -> {
                val password = (result as LoginResult.Password).phone
                Toast.makeText(
                    this.applicationContext, password,
                    Toast.LENGTH_SHORT
                ).show()
                binding.editTextPassword.setText(password)
            }
        }
    }
}