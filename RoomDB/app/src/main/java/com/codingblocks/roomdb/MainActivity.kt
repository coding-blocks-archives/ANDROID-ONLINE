package com.codingblocks.roomdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "User.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                db.userDao().insert(User("Pulkit Aggarwal", "99827939278", "Pitampura", 20))
            }
        }
        db.userDao().getAllUser().observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                with(list[list.size-1]) {
                    textView.text = name + id
                    textView2.text = age.toString()
                    textView3.text = address
                    textView4.text = number

                }
            }
        })



    }
}
