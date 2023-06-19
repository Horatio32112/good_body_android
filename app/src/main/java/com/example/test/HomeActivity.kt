package com.example.test

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.test.feature.profile.view.MyPersonalProfileActivity

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
                TODO("to MySetsRecords")
                true
            }
            R.id.menu_MyTimeRecords -> {
                TODO("to MyTimeRecords")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }







}