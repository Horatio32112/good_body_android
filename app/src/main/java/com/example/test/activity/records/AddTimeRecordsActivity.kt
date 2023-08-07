package com.example.test.activity.records

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.test.R
import com.example.test.activity.basics.HomeActivity
import com.example.test.api.ApiException
import com.example.test.data.Datasource
import kotlinx.coroutines.launch

class AddTimeRecordsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_time_records)
        val context = this
        val contentField: TextView = findViewById(R.id.add_time_ContentInput)
        val durationField: TextView = findViewById(R.id.add_time_DurationInput)
        val distanceField: TextView = findViewById(R.id.add_time_DistanceInput)
        val cancelBtn: Button = findViewById(R.id.add_time_CancelBtn)
        val addRecordBtn: Button = findViewById(R.id.add_time_CreateRecordBtn)

        cancelBtn.setOnClickListener {
            val intent = Intent(context, MyTimeRecordsActivity::class.java)
            context.startActivity(intent)
        }

        addRecordBtn.setOnClickListener {
            val content = contentField.text.toString()
            val duration: Int = durationField.text.toString().toInt()
            val distance = distanceField.text.toString().toFloat()
            val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("user_id", -1)

            lifecycleScope.launch{
                try{
                    Datasource.createTimeRecords(userId, content, duration, distance)
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                } catch (ex: ApiException) {
                    val toast = Toast.makeText(context, "create timeRecord failed", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }


}