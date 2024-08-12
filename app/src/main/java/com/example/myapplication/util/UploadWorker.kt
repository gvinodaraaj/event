package com.example.myapplication.util

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(context : Context, params : WorkerParameters): Worker(context,params) {

    override fun doWork(): Result {
        for(i : Int in 0..6000){
            Log.i("MYTAG","Uploading $i")
        }
        return Result.success()
    }
}