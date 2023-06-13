package com.example.test

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.model.PersonalProfileData
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
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
        val submit_button: Button = findViewById(R.id.button)
        val account_input: EditText = findViewById(R.id.account_field)

        submit_button.setOnClickListener {

            val account: String = account_input.getText().toString()
            api_connect(account)
        }

    }

    private fun api_connect(account:String) {


        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(LoggingInterceptor())
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            .build()
        val retrofitBuilder = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:3000")
            .build()
            .create(GetPersonalProfileApi::class.java)

        val inputMap = mapOf("account" to account)
        val retrofitData = retrofitBuilder.get_profile(account)
        retrofitData.enqueue(object : Callback<PersonalProfileData> {
            override fun onResponse(
                call: Call<PersonalProfileData>,
                response: Response<PersonalProfileData>
            ) {
                Log.d("header ", "test ${Thread.currentThread()}")

                if (response.isSuccessful) {
                    //API回傳結果
                    var profile : PersonalProfileData? = response.body()

                    // 在此處理 API 回應
                    val textView: TextView = findViewById<TextView>(R.id.return_field)
                    textView.text = profile?.height.toString()

                } else {
                    val textView: TextView = findViewById<TextView>(R.id.return_field)
                    textView.text="?"
                    // 處理 API 錯誤回應
                }
            }

            override fun onFailure(call: Call<PersonalProfileData>, t: Throwable) {
                val textView: TextView = findViewById<TextView>(R.id.return_field)
                textView.text = t.toString()

            }

        })
    }
}