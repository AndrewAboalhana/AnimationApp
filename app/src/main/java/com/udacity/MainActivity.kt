package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {
    private  val TAG = "MainActivity"
    private var downloadID: Long = 0
    private var URL = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter"

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)



        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        custom_button.setOnClickListener {
            download()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        }
    }

    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
        Log.i(TAG, "download:" + URL)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

    fun onRadioButtonChecked(view:View){
        if(view is RadioButton){
            val checked = view.isChecked

            when(view.getId()){
                R.id.firstRadio ->
                    if (checked){
                          URL = "https://github.com/bumptech/glide"
                    }
                R.id.secondRadio ->
                    if(checked){
                        URL = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter"
                    }
                R.id.thirdRadio ->
                    if (checked){
                        URL = "https://github.com/square/retrofit"
                    }
            }
        }
    }


    companion object {

        private const val CHANNEL_ID = "channelId"
    }



}