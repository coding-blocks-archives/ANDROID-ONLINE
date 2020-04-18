package com.codingblocks.workmanager.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.codingblocks.workmanager.GithubWorker
import com.codingblocks.workmanager.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workerBtn.setOnClickListener {
            setupGithubWorker()
        }
    }

    private fun setupGithubWorker() {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()

//        val worker = OneTimeWorkRequestBuilder<GithubWorker>()
//                .setInitialDelay(5,TimeUnit.SECONDS)
//                .setConstraints(constraints)
//                .build()

        val worker = PeriodicWorkRequestBuilder<GithubWorker>(1,TimeUnit.DAYS)
                .setInitialDelay(8,TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(this).enqueue(worker)
    }
}
