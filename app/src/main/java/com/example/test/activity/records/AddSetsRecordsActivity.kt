package com.example.test.activity.records

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.activity.basics.HomeActivity
import com.example.test.viewmodel.AddSetsRecordsViewModel

class AddSetsRecordsActivity : AppCompatActivity() {
    private val viewModel by viewModels<AddSetsRecordsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sets_records)
        val context = this
        val contentField: TextView = findViewById(R.id.add_sets_ContentInputField)
        val setsField: TextView = findViewById(R.id.add_sets_SetsInputField)
        val repsField: TextView = findViewById(R.id.add_sets_RepsInputField)
        val weightField: TextView = findViewById(R.id.add_sets_WeightInputField)
        val cancelBtn: Button = findViewById(R.id.add_sets_CancelBtn)
        val addRecordBtn: Button = findViewById(R.id.add_sets_CreateRecordBtn)

        cancelBtn.setOnClickListener {
            val intent = Intent(context, MySetsRecordsActivity::class.java)
            context.startActivity(intent)
        }

        addRecordBtn.setOnClickListener {
            val content = contentField.text.toString()
            val sets: Int = setsField.text.toString().toInt()
            val reps = repsField.text.toString().toInt()
            val weight = weightField.text.toString().toFloat()
            val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("user_id", -1)

            viewModel.createSetsRecords(userId, content, sets, reps, weight)

            viewModel.msgLiveData.observe(this) { msg ->
                msg ?: return@observe
                val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                toast.show()

                if (msg == "success") {
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
}