package com.udacity


import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
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
            if (status=="SUCCESS"){
                includeBinding.theStatus.text=status
            }else{
                includeBinding.theStatus.text=status
                includeBinding.theStatus.setTextColor(Color.parseColor("#FF0000"))
            }



        }
    }





    fun backToMainActivity(view: View) {
        val backIntent = Intent(this,MainActivity::class.java)
        startActivity(backIntent)
        finishAffinity()
    }




}
