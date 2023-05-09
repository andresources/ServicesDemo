package com.servicesdemo

import android.os.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var tv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv)

        val h: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                tv.text = msg.data.getString("mydata")
            }
        }
        for (i in 0..4) {
            //h.sendEmptyMessageDelayed(i, (3000 + i * 3000).toLong())
            var msg = Message()
            var b= Bundle()
            b.putString("mydata","Data : ${i}")
            msg.data = b
            h.sendMessageDelayed(msg, (3000 + i * 3000).toLong())

        }
    }
}