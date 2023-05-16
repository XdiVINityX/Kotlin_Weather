package com.example.kotlin_weather.view.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import com.example.kotlin_weather.R
import com.example.kotlin_weather.databinding.ActivityMainBinding
import com.example.kotlin_weather.view.contentProvider.ContentProviderFragment
import com.example.kotlin_weather.view.history.HistoryFragment


class MainActivity : AppCompatActivity() {

    companion object {
        private const val NOTIFICATION_ID_1 = 1
        private const val NOTIFICATION_ID_2 = 2
        private const val CHANNEL_ID_1 = "Важные"
        private const val CHANNEL_ID_2 = "остальные"
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance())
                .commit()
            pushNotification()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when(item.itemId){
           R.id.menuHistory ->{
               supportFragmentManager
                   .beginTransaction()
                   .add(R.id.fragment_container,HistoryFragment.newInstance())
                   .addToBackStack("")
                   .commit()
               true
           }
           R.id.menuProvider ->{
               supportFragmentManager
                   .beginTransaction()
                   .add(R.id.fragment_container,ContentProviderFragment.newInstance())
                   .addToBackStack("")
                   .commit()
               true
           }
           else -> super.onOptionsItemSelected(item)
       }
    }

    private fun pushNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilderOne = NotificationCompat.Builder(this, CHANNEL_ID_1).apply {
            setSmallIcon(R.drawable.ic_earth)
            setContentTitle("Заголовок для $CHANNEL_ID_1")
            setContentText("Сообщение для $CHANNEL_ID_1")
            priority = NotificationCompat.PRIORITY_MAX
        }
        val notificationBuilderTwo = NotificationCompat.Builder(this, CHANNEL_ID_2).apply {
            setSmallIcon(R.drawable.ic_russia)
            setContentTitle("Заголовок для $CHANNEL_ID_2")
            setContentText("Сообщение для $CHANNEL_ID_2")
            priority = NotificationCompat.PRIORITY_MAX
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelOneName = "Name $CHANNEL_ID_1"
            val descChannelOne = "Description $CHANNEL_ID_1"
            val importanceChannelOne = NotificationManager.IMPORTANCE_DEFAULT
            val channelOne =
                NotificationChannel(CHANNEL_ID_1, channelOneName, importanceChannelOne).apply {
                    description = descChannelOne
                }
            notificationManager.createNotificationChannel(channelOne)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nameTwoChannel = "Name $CHANNEL_ID_2"
            val descChannelTwo = "Description $CHANNEL_ID_2"
            val importanceChannelTwo = NotificationManager.IMPORTANCE_MIN
            val channelTwo =
                NotificationChannel(CHANNEL_ID_2, nameTwoChannel, importanceChannelTwo).apply {
                    description = descChannelTwo
                }
            notificationManager.createNotificationChannel(channelTwo)
        }

        notificationManager.notify(NOTIFICATION_ID_1, notificationBuilderOne.build())
        notificationManager.notify(NOTIFICATION_ID_2, notificationBuilderTwo.build())


    }






}