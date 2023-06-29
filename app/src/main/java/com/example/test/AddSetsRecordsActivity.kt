package com.example.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.model.OperationMsg
import com.example.test.model.PersonalProfileData
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
            val weight=weightField.getText().toString().toFloat()
            val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
            val user_id = sharedPreferences.getInt("user_id", -1)

            val okHttpClient = ApiSetUp.createOkHttpClient()
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
            var retrofitData1 = retrofitBuilder1.create_sets_records(user_id,content,sets,reps,weight)
            retrofitData1.enqueue(object : Callback<OperationMsg> {
                override fun onResponse(
                    call: Call<OperationMsg>,
                    response: Response<OperationMsg>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                        Log.d("header ", "user_id ${user_id} created a sets record")

                    } else {
                        Log.d("header ", "user_id ${user_id} failed to created a sets record\"")
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