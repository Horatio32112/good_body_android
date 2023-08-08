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
import com.example.test.adapter.SetsRecordItemAdapter
import com.example.test.viewmodel.MySetsRecordsViewModel

class MySetsRecordsActivity : AppCompatActivity() {
    private val viewModel by viewModels<MySetsRecordsViewModel>()

    class SetsRecordsBridge(private val viewModel: MySetsRecordsViewModel) {
        fun updateSetsRecords(
            recordId: Int, content: String, sets: Int,
            reps: Int,
            weight: Float
        ) {
            viewModel.updateSetsRecords(
                recordId,
                content,
                sets,
                reps,
                weight
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_sets_records)
        val addBtn: Button = findViewById(R.id.my_sets_records_AddRecordBtn)
        val homeBtn: Button = findViewById(R.id.my_sets_records_HomeBtn)

        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val account = sharedPreferences.getString("account", "")
        val context = this
        val recyclerView = findViewById<RecyclerView>(R.id.my_sets_records_RecyclerView)

        addBtn.setOnClickListener {
            val intent = Intent(context, AddSetsRecordsActivity::class.java)
            context.startActivity(intent)
        }
        homeBtn.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        viewModel.loadSetsRecords(account.toString())

        viewModel.setsRecordLiveData.observe(this) { records ->
            records ?: return@observe

            recyclerView.adapter = SetsRecordItemAdapter(records, SetsRecordsBridge(viewModel))
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

}