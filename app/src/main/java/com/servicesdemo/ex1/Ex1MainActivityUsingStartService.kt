package com.servicesdemo.ex1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.servicesdemo.R

class Ex1MainActivityUsingStartService : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex1_main_using_start_service)
    }

    fun fStartService(view: View) {
        startService(Intent(this,Ex1Service::class.java))
    }
    fun fStopService(view: View) {
        stopService((Intent(this,Ex1Service::class.java)))
    }
}