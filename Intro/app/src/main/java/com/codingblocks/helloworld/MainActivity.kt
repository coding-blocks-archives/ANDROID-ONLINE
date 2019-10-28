package com.codingblocks.helloworld

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

const val KEY = "KEY1"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            //Implicit Intent
            val intent = Intent(this, Main2Activity::class.java)
            intent.putExtra(KEY,"Pulkit")
            startActivity(intent)
        }

        //Explicit Intent
        button2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
            startActivity(intent)
        }

        Log.i("LIFECYCLE", "On Create Called")
    }

    override fun onStart() {
        super.onStart()
        Log.i("LIFECYCLE", "On Start Called")

    }

    override fun onResume() {
        super.onResume()
        Log.i("LIFECYCLE", "On Resume Called")

    }

    override fun onPause() {
        super.onPause()
        Log.i("LIFECYCLE", "On Pause Called")

    }

    override fun onStop() {
        super.onStop()
        Log.i("LIFECYCLE", "On Stop Called")

    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("LIFECYCLE", "On Destroy Called")


    }
}
