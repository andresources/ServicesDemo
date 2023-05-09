package com.servicesdemo.ex2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.servicesdemo.R

class Ex2MainActivityStartServiceUsingBind : AppCompatActivity() {
    private lateinit var mService: Ex2Service
    private var mBound: Boolean = false
    private lateinit var tv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex2_main_start_service_using_bind)
        tv = findViewById(R.id.tv)
    }

    fun fStartService(view: View) {
        val intent = Intent(this,Ex2Service::class.java)
        bindService(intent,connection, Context.BIND_AUTO_CREATE)

    }
    private val connection : ServiceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as Ex2Service.MyIBinder
            mService = binder.getMyService()
            mBound = true
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }

    }
    fun fStopService(view: View) {

            runOnUiThread({
                tv.text = "${mService.getMsg()}"
            })

    }
    override fun onStop() {
        super.onStop()
        // Disconnect MyService
       // unbindService(connection)
        mBound = false
    }
}