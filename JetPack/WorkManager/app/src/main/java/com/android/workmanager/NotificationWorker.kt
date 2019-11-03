package com.android.workmanager

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(
    val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters){
    override fun doWork(): Result {

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context,"first")
            .setContentTitle("Worker task")
            .setContentText("This is notification from worker class")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
        nm.notify(System.currentTimeMillis().toInt(),notification)

        return Result.success()

    }

}