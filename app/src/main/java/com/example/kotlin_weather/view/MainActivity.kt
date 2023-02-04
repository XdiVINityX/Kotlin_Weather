package com.example.kotlin_weather.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import com.example.kotlin_weather.R
import com.example.kotlin_weather.databinding.ActivityMainBinding
import com.example.kotlin_weather.databinding.ActivityMainWebviewBinding
import com.example.kotlin_weather.view.main.MainFragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val str = "https://gb.ru"

        binding.editTextViewUrl.setText(str)

        binding.buttonOk.setOnClickListener {
            showUrl(binding.editTextViewUrl.text.toString())
        }



      /*  setContentView(R.layout.activity_main)


        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance())
                .commit()
        }*/
    }

    fun showUrl(url: String) {
        Thread {
            val url = URL(url)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connectTimeout = 10000
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val result = getLines(reader)
            runOnUiThread {
                binding.webView.loadDataWithBaseURL(
                    null,
                    result,
                    "text/html; charset=utf-8",
                    "utf-8",
                    null
                )
            }
            urlConnection.disconnect()
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }


}