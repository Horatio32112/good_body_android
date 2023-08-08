package com.example.test.activity.records

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.activity.basics.HomeActivity
import com.example.test.activity.basics.MyPersonalProfileActivity
import com.example.test.activity.interactions.FindUserActivity
import com.example.test.adapter.TimeRecordItemAdapter
import com.example.test.viewmodel.MyTimeRecordsViewModel

class MyTimeRecordsActivity : AppCompatActivity() {
    private val viewModel by viewModels<MyTimeRecordsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_time_records)

        val homeBtn: Button = findViewById(R.id.my_time_records_HomeBtn)
        val addBtn: Button = findViewById(R.id.my_time_records_AddRecordBtn)
        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val account = sharedPreferences.getString("account", "")
        val context = this
        val recyclerView = findViewById<RecyclerView>(R.id.my_time_records_RecycleView)

        addBtn.setOnClickListener {
            val intent = Intent(context, AddTimeRecordsActivity::class.java)
            context.startActivity(intent)
        }

        homeBtn.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        viewModel.loadTimesRecords(account.toString())

        viewModel.timeRecordLiveData.observe(this) { records ->
            records ?: return@observe

            recyclerView.adapter = TimeRecordItemAdapter(records, context)
            recyclerView.setHasFixedSize(true)
        }

        viewModel.msgLiveData.observe(this) { msg ->
            msg ?: return@observe
            val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val context = this
        return when (item.itemId) {
            R.id.menu_FindUser -> {
                val intent = Intent(context, FindUserActivity::class.java)
                context.startActivity(intent)

                true
            }

            R.id.menu_MyProfile -> {
                val intent = Intent(context, MyPersonalProfileActivity::class.java)
                context.startActivity(intent)

                true
            }

            R.id.menu_MySetsRecords -> {
                val intent = Intent(context, MySetsRecordsActivity::class.java)
                context.startActivity(intent)
                true
            }

            R.id.menu_MyTimeRecords -> {
                val intent = Intent(context, MyTimeRecordsActivity::class.java)
                context.startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun updateTimeRecords(recordId: Int, content: String, duration: Int, distance: Float) {
        viewModel.updateTimeRecords(recordId, content, duration, distance)
    }
}