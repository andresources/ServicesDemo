package com.servicesdemo.ex5livedatacommunication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.servicesdemo.R

class Ex5LiveDataServiceMainActivity : AppCompatActivity() {
    private lateinit var tv : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex5_live_data_service_main)
        tv = findViewById(R.id.tv)
        setObservers()
    }

    private fun setObservers(){
        Ex5Service.timerInMillis.observe(this,{
            tv.setText("Time : "+TimerUtil.getFormattedTime(it,true))
        })
    }

    fun fStartService(view: View) {
        val myService = Intent(this,Ex5Service::class.java)
        startService(myService)
    }
}