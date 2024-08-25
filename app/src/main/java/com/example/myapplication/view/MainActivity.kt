package com.example.myapplication.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.view_model.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    private fun getCurrentFragment(): Fragment? {
        val fragmentManager = supportFragmentManager
        val fragments = fragmentManager.fragments
        return if (fragments.isNotEmpty()) fragments.last() else null
    }
    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fragment = getCurrentFragment()
        if (fragment is CustomBackPressHandler) {
            // Custom handling for specific fragment
            if (!fragment.handleBackPress()) {
            }
            else{
              super.onBackPressed()

            }
        } else {
           super.onBackPressed() // Proceed with default behavior if no custom handling
        }
    }
    interface CustomBackPressHandler {
        fun handleBackPress(): Boolean
    }
}