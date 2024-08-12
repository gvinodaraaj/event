package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MyApp
import com.example.myapplication.databinding.ActivityNewAccountBinding
import com.example.myapplication.util.NewActivityEnum
import com.example.myapplication.view_model.ListActivityViewModel
import com.example.myapplication.view_model.NewAccountViewModel
import com.example.myapplication.view_model.factory.ListActivityViewModelFactory
import com.example.myapplication.view_model.factory.NewAccountViewModelFactory
import com.example.myapplication.view_model.model.NewAccount

class NewAccountActivity : AppCompatActivity(), NewAccountViewModel.Callback {
    private lateinit var binding: ActivityNewAccountBinding
    private val viewModel: NewAccountViewModel by viewModels {
        NewAccountViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();
        binding.account = viewModel
        viewModel.setCallback(this)
        binding.button.setOnClickListener {
           val phon= viewModel.isExist(binding.editTxtPhone.text.toString().trim())
            val user = NewAccount(
                binding.editTxtName.text.toString().trim(),
                binding.editTxtPassword.text.toString().trim(),
                binding.editTxtRePassword.text.toString().trim(),
                binding.editTxtEmail.text.toString().trim(),
                binding.editTxtPhone.text.toString().trim()
            )
            viewModel.newAccount(user)
        }
    }

    override fun onFunctionTriggered(result: NewActivityEnum) {
        when (result) {
            NewActivityEnum.Mandatory -> {
                Toast.makeText(this, "Enter All Mandatory", Toast.LENGTH_SHORT).show()
            }

            NewActivityEnum.PasswordError -> {
                Toast.makeText(this, "password not matching", Toast.LENGTH_SHORT).show()
            }

            NewActivityEnum.Sucess -> {
                val intent = Intent(this, ListActivity::class.java)
                intent.putExtra("NewActivity", true)
                startActivity(intent)
                finish()
            }
        }

    }
}