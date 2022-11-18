package com.udacity

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.udacity.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    var binding:ActivityDetailBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
        val includeBinding= binding!!.include
        setSupportActionBar(toolbar)

        val notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()

        if (intent.extras != null){
            var title=intent!!.extras!!.getString("title").toString()
            var status=intent!!.extras!!.getString("status").toString()
            includeBinding.name.text=title
            includeBinding.theStatus.text=status

        }
    }



    fun backToMainActivity(view: View) {
        val backIntent = Intent(this,MainActivity::class.java)
        startActivity(backIntent)
    }

}
