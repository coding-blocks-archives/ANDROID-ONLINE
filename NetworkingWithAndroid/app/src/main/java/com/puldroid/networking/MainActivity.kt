package com.puldroid.networking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClient()


        val request = Request.Builder()
            .url("https://api.github.com/users/aggarwalpulkit596")
            .build()

        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()


        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute().body?.string()
            }
            val user = gson.fromJson<User>(response, User::class.java)
            textView.text = user.name
            textView2.text = user.login
            Picasso.get().load(user.avatarUrl).into(imageView)

        }
    }

}