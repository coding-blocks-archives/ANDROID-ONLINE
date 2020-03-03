package com.puldroid.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = Bundle()
        bundle.putString("KEY", "Pulkit")
        val fragment = FirstFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()

//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.container, BlankFragment())
//            .commitNow()
//
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.container, FirstFragment())
//            .commitNow()
    }
}
