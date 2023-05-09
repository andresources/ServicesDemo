package com.servicesdemo.ex3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.servicesdemo.R
import com.servicesdemo.ex2.Ex2Service

class Ex3MainActivityWithLiveData : AppCompatActivity() {
    private lateinit var textView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex3_main_with_live_data)
        textView = findViewById(R.id.textView)

    }

    fun fStartService(view: View) {
        val intent: Intent = Intent(applicationContext,Ex3Service::class.java)
        startService(intent)
    }
}