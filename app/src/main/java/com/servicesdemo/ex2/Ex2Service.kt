package com.servicesdemo.ex2

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

import android.widget.Toast
import com.servicesdemo.ex1.Ex1Service

class Ex2Service : Service(){
    private val binder = MyIBinder()
    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this,"onCreate()", Toast.LENGTH_LONG).show()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this,"onStartCommand",Toast.LENGTH_LONG).show()

        //stopSelf() or stopService() manually call these methods if you override onStartCommand()

        return START_STICKY_COMPATIBILITY
    }
    override fun onBind(intent: Intent?): IBinder? = binder

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"onDestroy",Toast.LENGTH_LONG).show()
    }

     fun getMsg() = "I am from Service...."

    inner class MyIBinder : Binder() {
        fun getMyService() : Ex2Service
        {
            return this@Ex2Service
        }
    }

}