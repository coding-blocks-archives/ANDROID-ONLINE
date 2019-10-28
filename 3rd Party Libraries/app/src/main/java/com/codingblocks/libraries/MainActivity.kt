package com.codingblocks.libraries

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val okHttpClient = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.github.com/users/aggarwalpulkit596")
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("NETWORKING", e.localizedMessage.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val result = response.body?.string()

                    val gson = Gson().newBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()


                    val user = gson.fromJson(result,User::class.java)

                    runOnUiThread {
                        Picasso.get().load(user.avatarUrl).fit().into(imageView)
                        userNameTv.text = user.login
                        nameTv.text = user.name
                        bioTv.text = user.bio
                    }
                }
            }

        })


    }
}


data class User(
    val login: String,
    val avatarUrl: String,
    val name: String,
    val bio: String
)
