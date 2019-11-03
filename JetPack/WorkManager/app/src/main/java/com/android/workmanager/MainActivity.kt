package com.android.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(
                NotificationChannel(
                    "first",
                    "default",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        button.setOnClickListener {
            scheduleRepeatingNotification()
        }

    }

    private fun scheduleRepeatingNotification() {
        val constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.UNMETERED)
            setRequiresBatteryNotLow(true)
        }.build()


        val workerRequest = PeriodicWorkRequestBuilder<NotificationWorker>(30,TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    private fun scheduleNotification() {
        val constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.UNMETERED)
            setRequiresBatteryNotLow(true)
        }.build()


        val workerRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(workerRequest)
    }
}
