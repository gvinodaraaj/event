package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.sharedpreference.PreferencesHelper
import com.example.myapplication.databinding.SplashMainBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: SplashMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SplashMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = Handler(Looper.getMainLooper())

        val task = Runnable {
            if (PreferencesHelper(this).isLogin()) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

        handler.postDelayed(task, 1500)
    }

}

