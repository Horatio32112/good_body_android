package com.example.test

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.model.User
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val register_button: Button = findViewById(R.id.register_button)
        val to_login_button: Button = findViewById(R.id.to_login_button)
        val account_input: EditText = findViewById(R.id.account_input_field)
        val password_input: EditText = findViewById(R.id.password_input_field)

        to_login_button.setOnClickListener {
            val context = to_login_button.context
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }


        register_button.setOnClickListener {

            val account: String = account_input.getText().toString()
            val password: String = password_input.getText().toString()
            if (TextUtils.isEmpty(account)) {
                account_input.setError("This block cannot be blank")

            } else if (TextUtils.isEmpty(password)) {
                password_input.setError("This block cannot be blank")

            } else {
                val toast = Toast.makeText(this, "info_submitted", Toast.LENGTH_SHORT)
                toast.show()
            }


/**
            api_connect()
*/
        }
    }
    /**
    private fun api_connect() {

        val listView = findViewById<ListView>(R.id.)
        listView.adapter = SimpleAdapter()

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(LoggingInterceptor())
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            .build()
        val retrofitBuilder = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
            .create(JsonPlaceholderService::class.java)
        val retrofitData = retrofitBuilder.posts()
        retrofitData.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                Log.d("header ","test ${Thread.currentThread()}")

                if (response.isSuccessful) {
                    //API回傳結果
                    val sb = StringBuffer()
                    response.body()?.forEach { user ->
                        sb.append(user.body)
                        sb.append("\n")
                        sb.append("---------------------\n")
                    }
                    // 在此處理 API 回應
                    val textView: TextView = findViewById<TextView>(R.id.textView2)
                    textView.text = sb.toString()
                } else {
                    // 處理 API 錯誤回應
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })*/
    }

