package com.codingblocks.roomdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "User.db"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {
            db.userDao().insert(User("Pulkit Aggarwal","99827939278","Pitampura",20))
        }
        button2.setOnClickListener {
            val list = db.userDao().getAllUser()
            if(list.isNotEmpty()){
                with(list[0]){
                    textView.text = name
                    textView2.text = age.toString()
                    textView3.text = address
                    textView4.text = number

                }
            }
        }



    }
}
