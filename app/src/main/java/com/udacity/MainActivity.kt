package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.core.content.ContextCompat
import com.udacity.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {
    private  val TAG = "MainActivity"
    private var downloadID: Long = 0
    private var URL : String? = null
    private var title : String? = null
    private var downloadManager: DownloadManager? = null


    var binding :ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
        setSupportActionBar(toolbar)

        createChannel(
            getString(R.string.channel_id),
            getString(R.string.channel_name)
        )

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        custom_button.setOnClickListener {
            custom_button.buttonState = ButtonState.Clicked
            download()


        }
    }


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if(id==downloadID){
                custom_button.buttonState = ButtonState.Completed
                val notificationManager = ContextCompat.getSystemService(
                    applicationContext,
                    NotificationManager::class.java
                ) as NotificationManager
                        notificationManager.sendNotification(title.toString(),
                            downloadStatus(downloadID).toString(),applicationContext)

                    }

                }


            }


    private fun download() {
        val notificationManager =
            ContextCompat.getSystemService(
                this,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.cancelNotifications()
        if (URL==null){
            Toast.makeText(this,"Please select the file to download",Toast.LENGTH_SHORT).show()
        }
        else {
            val request =
                DownloadManager.Request(Uri.parse(URL))
                    .setTitle(getString(R.string.app_name))
                    .setDescription(getString(R.string.app_description))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)
            Log.i(TAG, "download:" + URL)

            downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            downloadID = downloadManager!!.enqueue(request)// enqueue puts the download request in the queue.// enqueue puts the download request in the queue.
        }
    }

    fun onRadioButtonChecked(view:View){
        if(view is RadioButton){
            val checked = view.isChecked

            when(view.getId()){
                R.id.firstRadio ->
                    if (checked){
                          URL = GLIDE_APP_URL
                          title="Glide_image Loading Library by Bump Tech"
                    }
                R.id.secondRadio ->
                    if(checked){
                        URL = MAIN_APP_URL
                        title = "LoadApp-Current repository by Udacity"
                    }
                R.id.thirdRadio ->
                    if (checked){
                        URL = RETROFIT_APP_URL
                        title = "Retrofit-Type-safe HTTP client for Android and Java by Square,Inc"
                    }
            }
        }
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,

                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Download Notification"

            val notificationManager = this.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun downloadStatus(downloadIdRef: Long): String? {

        var downloadStatus: String ?="FAIL"

        val downloadManagerQuery = DownloadManager.Query()
        downloadManagerQuery.setFilterById(downloadIdRef)

        val cursor: Cursor? = downloadManager?.query(downloadManagerQuery)

        if (cursor != null) {
            Log.e(TAG, "downloadStatus: " + cursor )
            if (cursor.moveToFirst()) {

                val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)

                when (cursor.getInt(columnIndex)) {

                    DownloadManager.STATUS_FAILED -> {
                        downloadStatus ="FAIL"
                        Log.e(TAG, "downloadStatus: "+ downloadStatus )
                    }

                    DownloadManager.STATUS_SUCCESSFUL -> {
                        downloadStatus ="SUCCESS"
                        Log.e(TAG, "downloadStatus: " + downloadStatus )
                    }


                }

            }
        }
        return downloadStatus
    }

    companion object{
        const val MAIN_APP_URL = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        const val RETROFIT_APP_URL = "https://github.com/square/retrofit/archive/refs/heads/master.zip"
        const val GLIDE_APP_URL = "https://github.com/bumptech/glide/archive/refs/heads/master.zip"
    }

}



