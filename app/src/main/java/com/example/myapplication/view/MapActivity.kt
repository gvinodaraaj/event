package com.example.myapplication.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.R
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.dao.UserDao
import com.example.myapplication.data.db.model.User
import com.example.myapplication.databinding.MapMainBinding
import com.example.myapplication.util.UploadWorker
import com.example.myapplication.util.isNetworkAvailable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MapActivity : AppCompatActivity() , OnMapReadyCallback {

    private lateinit var binding: MapMainBinding
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        userDao = db.userDao()
        var users:MutableList<User> = mutableListOf()

       // itemAdapter = ItemAdapter(users)
       // recyclerView.adapter = itemAdapter

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.gmap) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        override fun onMapReady(googleMap: GoogleMap) {
            mMap = googleMap

            // Add a marker and move the camera
            val locations = listOf(
                LatLng(13.110917, 80.102194),  // First location
                LatLng(12.971598, 77.594566),  // Second location (Bangalore)
                LatLng(19.076090, 72.877426),  // Third location (Mumbai)
                LatLng(28.704060, 77.102493)   // Fourth location (Delhi)
            )

            // Adding markers for each location
            for (location in locations) {
                mMap.addMarker(MarkerOptions().position(location).title("Marker at (${location.latitude}, ${location.longitude})"))
            }

            // Move the camera to the first location in the list
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations[0], 5f))
            mMap.setOnMapClickListener { latLng ->
                // Clear existing markers
                mMap.uiSettings.isZoomControlsEnabled = true

                //mMap.clear()
                // Add a marker at the tapped location
               // mMap.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
                // Optionally move camera to the selected location
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

                // Here you can get the latitude and longitude
                val selectedLatitude = latLng.latitude
                val selectedLongitude = latLng.longitude

                // Do something with the selected location (e.g., save or display coordinates)
                // For example: show a toast with the coordinates
                Toast.makeText(this, "Selected Location: ($selectedLatitude, $selectedLongitude)", Toast.LENGTH_LONG).show()
            }
        }


}
