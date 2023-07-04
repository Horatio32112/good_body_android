package com.example.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.model.OperationMsg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTimeRecordsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_time_records)
        val context=this
        val contentField:TextView=findViewById(R.id.add_time_ContentInput)
        val durationField:TextView=findViewById(R.id.add_time_DurationInput)
        val distanceField:TextView=findViewById(R.id.add_time_DistanceInput)
        val cancelBtn: Button =findViewById(R.id.add_time_CancelBtn)
        val addRecordBtn:Button=findViewById(R.id.add_time_CreateRecordBtn)

        cancelBtn.setOnClickListener{
            val intent = Intent(context, MyTimeRecordsActivity::class.java)
            context.startActivity(intent)
        }

        addRecordBtn.setOnClickListener{
            val content=contentField.text.toString()
            val duration:Int=durationField.text.toString().toInt()
            val distance=distanceField.text.toString().toFloat()
            val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
            val user_id = sharedPreferences.getInt("user_id", -1)

            val okHttpClient = ApiSetUp.createOkHttpClient()
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
            var retrofitData1 = retrofitBuilder1.create_time_records(user_id,content,duration, distance)
            retrofitData1.enqueue(object : Callback<OperationMsg> {
                override fun onResponse(
                    call: Call<OperationMsg>,
                    response: Response<OperationMsg>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                        Log.d("header ", "user_id ${user_id} created a time record")

                    } else {
                        Log.d("header ", "user_id ${user_id} failed to created a time record\"")
                        // 處理 API 錯誤回應
                    }
                }
                override fun onFailure(call: Call<OperationMsg>, t: Throwable) {
                    Log.d("header ", "call create time record api failed")
                }
            })
        }




    }





}