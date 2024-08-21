package com.example.myapplication.view

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.MyApp
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.dao.UserDao
import com.example.myapplication.data.db.model.Event
import com.example.myapplication.data.db.model.User
import com.example.myapplication.databinding.HomeMainBinding
import com.example.myapplication.util.UploadWorker
import com.example.myapplication.util.isNetworkAvailable
import com.example.myapplication.view.adapter.ItemAdapter
import com.example.myapplication.view.adapter.ToDoAdapter
import com.example.myapplication.view_model.HomeActivityViewModel
import com.example.myapplication.view_model.ListActivityViewModel
import com.example.myapplication.view_model.factory.HomeActivityViewModelFactory
import com.example.myapplication.view_model.factory.ListActivityViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeMainBinding
    private val viewModel: HomeActivityViewModel by viewModels {
        HomeActivityViewModelFactory((application as MyApp).repositoryEvent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.items.observe(this, Observer { items ->
            recyclerView.adapter = ToDoAdapter(items as MutableList<Event>)
        })


       /* CoroutineScope(Dispatchers.IO).launch {
            //    val userId =  userDao.insert(User(name = "vinod", password = "3123", phone = "9600104721", mail = "g.vinodaraaj@gmail.com"))
            //    println("Inserted user ID: $userId")
            CoroutineScope(Dispatchers.Main).launch {
                setOnwTimeWorkRequest()
            }
            // Retrieve all users
            //   updateItems(userDao.getAll().toMutableList())
        }*/
        binding.add.setOnClickListener{
            val intent = Intent(this, NewEventActivity::class.java)
            intent.putExtra("FromHome", true)
            startActivity(intent)
        }

        binding.toMap.setOnClickListener(){
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("FromHome", true)
            startActivity(intent)
        }
        binding.toProfile.setOnClickListener(){
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra("FromHome", true)
            startActivity(intent)
        }

    }
    @SuppressLint("SuspiciousIndentation")
    private fun setOnwTimeWorkRequest(){
        val workManager = WorkManager.getInstance(applicationContext)
        //for constraints
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(uploadRequest)
        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this, Observer {
                if (isNetworkAvailable(this)) {
                    // Network is available, proceed with network operations
                    binding.txtHeading .text = it.state.name+"Connected"

                } else {
                    // Show a message to the user
                    binding.txtHeading .text = it.state.name+"Not Connected"
                }
            })
    }
    override fun onResume() {
        super.onResume()
        viewModel.getAllUsers()
    }

}