package com.servicesdemo.ex5livedatacommunication

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Ex5Service : LifecycleService(){
    companion object{
        val timerInMillis = MutableLiveData<Long>()
    }
    private var isServiceStopped = false
    override fun onCreate() {
        super.onCreate()
        initValues()
    }
    private fun initValues(){
        isServiceStopped = false
        timerInMillis.value = 0L
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startTimer()
        return START_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
        isServiceStopped = true
    }
    private var lapTime = 0L
    private var timeStarted = 0L
    private fun startTimer(){
        timeStarted = System.currentTimeMillis()
        CoroutineScope(Dispatchers.Main).launch {
            while (!isServiceStopped){
                lapTime = System.currentTimeMillis() - timeStarted
                timerInMillis.value = lapTime
                delay(10L)
            }
        }
    }
}