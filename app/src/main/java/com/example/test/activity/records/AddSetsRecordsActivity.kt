package com.example.test.activity.records

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.test.R
import com.example.test.activity.basics.HomeActivity
import com.example.test.api.ApiException
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.data.Datasource
import com.example.test.model.OperationMsg
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSetsRecordsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sets_records)
        val context=this
        val contentField:TextView=findViewById(R.id.add_sets_ContentInputField)
        val setsField:TextView=findViewById(R.id.add_sets_SetsInputField)
        val repsField:TextView=findViewById(R.id.add_sets_RepsInputField)
        val weightField:TextView=findViewById(R.id.add_sets_WeightInputField)
        val cancelBtn: Button =findViewById(R.id.add_sets_CancelBtn)
        val addRecordBtn:Button=findViewById(R.id.add_sets_CreateRecordBtn)

        cancelBtn.setOnClickListener{
            val intent = Intent(context, MySetsRecordsActivity::class.java)
            context.startActivity(intent)
        }

        addRecordBtn.setOnClickListener{
            val content=contentField.text.toString()
            val sets:Int=setsField.text.toString().toInt()
            val reps=repsField.text.toString().toInt()
            val weight=weightField.text.toString().toFloat()
            val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("user_id", -1)

            lifecycleScope.launch{
                try{
                    Datasource.createSetsRecords(userId,content,sets,reps,weight)
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                } catch (ex: ApiException) {
                    val toast = Toast.makeText(context, "create setsRecord failed", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
    }
}