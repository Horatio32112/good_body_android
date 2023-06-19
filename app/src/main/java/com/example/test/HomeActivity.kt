package com.example.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.api.GetPersonalProfileApi
import com.example.test.api.LoggingInterceptor
import com.example.test.databinding.ActivityMainBinding
import com.example.test.model.PersonalProfileData
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val context = this
        return when (item.itemId) {
            R.id.menu_MyProfile -> {
                val intent = Intent(context, MyPersonalProfileActivity::class.java)
                context.startActivity(intent)

                true
            }
            R.id.menu_MySetsRecords -> {

                true
            }
            R.id.menu_MyTimeRecords -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }







}