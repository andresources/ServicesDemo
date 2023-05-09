package com.servicesdemo.ex3

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData

class Ex3Service : LifecycleService() {
    val BUS = MutableLiveData<String>()
    override fun onCreate() {
        super.onCreate()

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        BUS.value = "I am From Service...."
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}