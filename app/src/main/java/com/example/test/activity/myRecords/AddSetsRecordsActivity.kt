package com.example.test.activity.myRecords

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.activity.basics.HomeActivity
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.model.OperationMsg
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

            val okHttpClient = ApiSetUp.createOkHttpClient()
            val apiBuilder = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
            val apiCaller = apiBuilder.createSetsRecords(userId,content,sets,reps,weight)
            apiCaller.enqueue(object : Callback<OperationMsg> {
                override fun onResponse(
                    call: Call<OperationMsg>,
                    response: Response<OperationMsg>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                        Log.d("header ", "user_id $userId created a sets record")

                    } else {
                        Log.d("header ", "user_id $userId failed to created a sets record\"")
                        // 處理 API 錯誤回應
                    }
                }
                override fun onFailure(call: Call<OperationMsg>, t: Throwable) {
                    Log.d("header ", "call create sets record api failed")
                }
            })
        }




    }





}