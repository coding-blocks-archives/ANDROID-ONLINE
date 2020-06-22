package com.puldroid.androidwidgets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView1.isVisible = true
        textView1.visibility = View.INVISIBLE
        textView1.text = "Hello Pulkit"
        textView1.append("How are you doing")
        editTextTextPersonName.requestFocus()
    }
}