package com.servicesdemo.ex1

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class Ex1Service : Service() {
    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this,"onCreate()",Toast.LENGTH_LONG).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this,"onStartCommand",Toast.LENGTH_LONG).show()

        //stopSelf() or stopService() manually call these methods if you override onStartCommand()

        return START_STICKY_COMPATIBILITY
    }
    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(this,"onBind",Toast.LENGTH_LONG).show()
        return null
    }


    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"onDestroy",Toast.LENGTH_LONG).show()
    }
}