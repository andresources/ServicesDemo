package com.servicesdemo.ex4foregroundservice

import android.R
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Ex4Service : Service() {
    override fun onCreate() {
        super.onCreate()
    }

    lateinit var notificationManager: NotificationManager
    private val CHANNEL_ID = "i.apps.notifications"
    private val CHANNEL_NAME = "i.apps.notifications.ch"
    private val STOP = "stop_service"
    private var isServiceStopped:Boolean  = false
    private lateinit var notification_builder: NotificationCompat.Builder
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            STOP ->{
                //stopForeground(STOP_FOREGROUND_DETACH)
                isServiceStopped = true
                stopSelf()

            }

        }
        //1. Notification Manager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //2. Notification Channel
        createNotifChannel()
        startTimer()
        //3. PendingIntent
        /* For Activity Launch
        val pendingIntent=PendingIntent.getActivity(this, 420,
            Intent(this, Ex4Service::class.java).apply {
                this.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            },
            PendingIntent.FLAG_IMMUTABLE
        )*/

        val stopPendingIntent=PendingIntent.getService(this, 420,
            Intent(this, Ex4Service::class.java).apply {
                this.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                this.action = STOP
            },
            PendingIntent.FLAG_IMMUTABLE
        )

        //4. Notification Builder
             notification_builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Sample Title")
            .setContentText("This is sample body notif")
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.drawable.sym_def_app_icon,"Stop",stopPendingIntent)
            //.setContentIntent(pendingIntent) //In-Case you want start Activity

        //notificationManager.notify(22,notification_builder) ->Show Normal Notifications.
        //Start Foreground Service
        startForeground(22,notification_builder.build())

        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                enableLights(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
    private var cnt = 0;
    private fun startTimer() {

        CoroutineScope(Dispatchers.Main).launch {
            while (!isServiceStopped){
                delay(1000)
                cnt++
                notification_builder.setContentText(
                    "Count : ${cnt}"
                )
                notificationManager.notify(22, notification_builder.build())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
