package com.servicesdemo.ex5livedatacommunication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.servicesdemo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Ex5Service : LifecycleService(){
    companion object{
        val timerInMillis = MutableLiveData<Long>()
        val timeEvents = MutableLiveData<TimeEvents>()
    }
    private var isServiceStopped = false
    override fun onCreate() {
        super.onCreate()
        initValues()
        setupNotifications()
    }
    private fun initValues(){
        isServiceStopped = false
        timerInMillis.value = 0L
        timeEvents.value = TimeEvents.END
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.let {
            when(it.action){
                 ACTION_START_SERVICE ->{
                     startForegroundService()
                 }

                ACTION_STOP_SERVICE ->{
                    stopService()
                }
                else -> {}
            }

        }
        return START_STICKY
    }
    private fun startForegroundService() {
        timeEvents.value = TimeEvents.START
        startTimer()
        startForeground(NOTIFICATION_ID, mNotificationBuilder.build())
        timerInMillis.observe(this,{
            mNotificationBuilder.setContentText(TimerUtil.getFormattedTime(it,false))
            mNotificationManager.notify(NOTIFICATION_ID,mNotificationBuilder.build())
        })
    }
    private fun stopService(){
        isServiceStopped = true
        timeEvents.value = TimeEvents.END
        initValues()
        mNotificationManager.cancel(NOTIFICATION_ID)
        //stopForeground(true)
        stopForeground(STOP_FOREGROUND_DETACH)
        stopSelf()

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
            while (!isServiceStopped&&timeEvents.value!! ==TimeEvents.START){
                lapTime = System.currentTimeMillis() - timeStarted
                timerInMillis.value = lapTime
                delay(50L)
            }
        }
    }


    private lateinit var mNotificationManager: NotificationManager
    private lateinit var mNotificationBuilder: NotificationCompat.Builder
    private fun setupNotifications(){
            //Step1:
            mNotificationManager= getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            //Step2: Notification Channel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance)
                mNotificationManager.createNotificationChannel(mChannel)
            }

            //Step3: Create Pending Intent
            val pendingIntent= PendingIntent.getService(this, 420,
                Intent(this, Ex5Service::class.java).apply {
                    this.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    action = ACTION_STOP_SERVICE
                },
                PendingIntent.FLAG_IMMUTABLE
            )

            //Step4: Notification.Builder
            mNotificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(androidx.vectordrawable.animated.R.drawable.notification_bg_normal_pressed)
                .setContentTitle("My notification")
                .setContentText("Hello World!").addAction(androidx.constraintlayout.widget.R.drawable.abc_ic_clear_material,"Delete",pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                //.setContentIntent(pendingIntent)
                //.setStyle() -> BigText,BigImage
                .setAutoCancel(true)
        }

}