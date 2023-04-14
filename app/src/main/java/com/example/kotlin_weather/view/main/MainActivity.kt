package com.example.kotlin_weather.view.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.kotlin_weather.R
import com.example.kotlin_weather.databinding.ActivityMainBinding
import com.example.kotlin_weather.view.contentProvider.ContentProviderFragment
import com.example.kotlin_weather.view.history.HistoryFragment


class MainActivity : AppCompatActivity() {
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






}