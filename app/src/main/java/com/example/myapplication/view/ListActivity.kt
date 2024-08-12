package com.example.myapplication.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.MyApp
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.dao.UserDao
import com.example.myapplication.data.db.model.User
import com.example.myapplication.databinding.ListMainBinding
import com.example.myapplication.util.UploadWorker
import com.example.myapplication.util.isNetworkAvailable
import com.example.myapplication.view.adapter.ItemAdapter
import com.example.myapplication.view_model.ListActivityViewModel
import com.example.myapplication.view_model.factory.ListActivityViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ListMainBinding
    private val viewModel: ListActivityViewModel by viewModels {
        ListActivityViewModelFactory((application as MyApp).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.items.observe(this, Observer { items ->
            recyclerView.adapter = ItemAdapter(items as MutableList<User>)
        })

        binding.add.setOnClickListener {
          //  viewModel.addItem()
            val intent= Intent(this, NewAccountActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setOnwTimeWorkRequest() {
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
                    //      binding.txtHeading .text = it.state.name+"Connected"
                } else {
                    // Show a message to the user
                    //     binding.txtHeading .text = it.state.name+"Not Connected"
                }
            })
    }

    private fun updateItems(newItem: MutableList<User>) {
        //      itemAdapter.updateList(newItem)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllUsers()
    }

}