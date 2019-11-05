package com.android.sharedprefs

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var appCount = 0
    val KEY_OPEN = "key_open"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getPreferences(Context.MODE_PRIVATE)

        appCount = prefs.getInt(KEY_OPEN,-1)

        appCount++

        prefs.edit {
            putInt(KEY_OPEN,appCount)
        }

        //HashMap
        //(Key,value)
        counter.text = appCount.toString()


    }
}
