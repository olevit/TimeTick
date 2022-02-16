package com.example.timetick

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private  val tickReceiver by lazy { makeBroadcastReceiver() }
    companion object{
        private  fun getCurrentTimeStamp(): String{
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            val now = Date()
            return simpleDateFormat.format(now)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        dateTimeTextView.text = getCurrentTimeStamp()
        registerReceiver(tickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun onPause() {
        super.onPause()
        try{
            unregisterReceiver(tickReceiver)
        }catch (e: IllegalArgumentException){
            Log.e("Broadcast", "Time tick Receiver not registered",e)
        }
    }

    private fun makeBroadcastReceiver():BroadcastReceiver{
        return object :BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                if(intent?.action == Intent.ACTION_TIME_TICK){
                    dateTimeTextView.text = getCurrentTimeStamp()
                }//TODO("Not yet implemented")
            }
        }
    }
}
