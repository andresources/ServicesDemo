package com.servicesdemo.ex4foregroundservice

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.servicesdemo.R

class ForegroundServiceMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreground_service_main)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fStartService(view: View) {
        val intentS = Intent(this,Ex4Service::class.java)
        startService(intentS)
    }
}