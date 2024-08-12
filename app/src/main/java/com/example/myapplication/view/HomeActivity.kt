package com.example.myapplication.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.dao.UserDao
import com.example.myapplication.data.db.model.User
import com.example.myapplication.databinding.HomeMainBinding
import com.example.myapplication.util.UploadWorker
import com.example.myapplication.util.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeMainBinding
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        userDao = db.userDao()
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        var users:MutableList<User> = mutableListOf()

       // itemAdapter = ItemAdapter(users)
       // recyclerView.adapter = itemAdapter


        binding.add.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
            //    val userId =  userDao.insert(User(name = "vinod", password = "3123", phone = "9600104721", mail = "g.vinodaraaj@gmail.com"))
            //    println("Inserted user ID: $userId")
                CoroutineScope(Dispatchers.Main).launch {
                    setOnwTimeWorkRequest()
                }
                // Retrieve all users
             //   updateItems(userDao.getAll().toMutableList())
            }
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
    private fun getItems() : MutableList<User>{
       var users:MutableList<User> = mutableListOf()
        CoroutineScope(Dispatchers.IO).launch {
            users = userDao.getAll().toMutableList()
        }
        return users
    }
    private fun updateItems(newItem:MutableList<User>) {
  //      itemAdapter.updateList(newItem)
    }

}