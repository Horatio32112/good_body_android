package com.example.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.api.ApiSetUp
import com.example.test.api.GetUserIdByAccountApi
import com.example.test.api.RegisterAccountApi
import com.example.test.model.OperationMsg
import com.example.test.model.UserId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val context: Context = this
        val register_button: Button = findViewById(R.id.register_button)
        val to_login_button: Button = findViewById(R.id.register_to_login_button)
        val account_input: EditText = findViewById(R.id.register_account_input_field)
        val password_input: EditText = findViewById(R.id.register_password_input_field)
        val email_input: EditText = findViewById(R.id.register_email_input_field)
        val name_input: EditText = findViewById(R.id.register_name_input_field)
        val height_input: EditText = findViewById(R.id.register_height_input_field)
        val weight_input: EditText = findViewById(R.id.register_weight_input_field)
        val gender_input: EditText = findViewById(R.id.register_gender_input_field)
        val age_input: EditText = findViewById(R.id.register_age_input_field)
        val phone_number_input: EditText = findViewById(R.id.register_phone_number_input_field)

        to_login_button.setOnClickListener {
            val context = to_login_button.context
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }


        register_button.setOnClickListener {

            val account: String = account_input.getText().toString()
            val password: String = password_input.getText().toString()
            val name: String = name_input.getText().toString()
            val phone_number: String = phone_number_input.getText().toString()
            val email: String = email_input.getText().toString()
            val gender: String = gender_input.getText().toString()
            val height: Int = height_input.getText().toString().toInt()
            val weight: Int = weight_input.getText().toString().toInt()
            val age: Int = age_input.getText().toString().toInt()


            if (TextUtils.isEmpty(account)||
                TextUtils.isEmpty(name)||
                TextUtils.isEmpty(email)||
                TextUtils.isEmpty(phone_number)||
                TextUtils.isEmpty(password)
                ) {
                TODO("warn about empty blanks")
            }else{
                val okHttpClient = ApiSetUp.createOkHttpClient()
                var retrofitBuilder1 = ApiSetUp.createRetrofit<RegisterAccountApi>(okHttpClient)
                var retrofitData1 = retrofitBuilder1.register_account(email,password,account,phone_number,name,age,height,weight,gender)
                retrofitData1.enqueue(object : Callback<OperationMsg> {
                    override fun onResponse(
                        call: Call<OperationMsg>,
                        response: Response<OperationMsg>
                    ) {
                        Log.d("header ", "test ${Thread.currentThread()}")

                        if (response.isSuccessful) {
                            //API回傳結果
                            val toast = Toast.makeText(context, response.body()?.Msg, Toast.LENGTH_SHORT)
                            toast.show()

                            var retrofitBuilder2 = ApiSetUp.createRetrofit<GetUserIdByAccountApi>(okHttpClient)
                            var retrofitData2 = retrofitBuilder2.get_id(account)
                            retrofitData2.enqueue(object : Callback<UserId> {
                                override fun onResponse(
                                    call: Call<UserId>,
                                    response: Response<UserId>
                                ) {
                                    Log.d("header ", "test ${Thread.currentThread()}")

                                    if (response.isSuccessful) {
                                        //API回傳結果
                                        var id : Int? = response.body()?.id

                                        val sharedPreferences = context.getSharedPreferences("account_info", Context.MODE_PRIVATE)
                                        val editor = sharedPreferences.edit()
                                        editor.putString("account", account)
                                        id?.let { it1 -> editor.putInt("user_id", it1) }
                                        editor.apply()

                                        val intent = Intent(context, HomeActivity::class.java)
                                        context.startActivity(intent)

                                        Log.d("header ", "user exists, id = ${id}")
                                        Log.d("header ", "${account} registered and logged in.")

                                    } else {
                                        Log.d("header ", "something went wrong when registering")
                                        // 處理 API 錯誤回應
                                    }
                                }
                                override fun onFailure(call: Call<UserId>, t: Throwable) {
                                    Log.d("header ", "GetUserIdByAccountApi call failed")
                                }

                            })

                        } else {
                            Log.d("header ", "something went wrong when registering")
                            // 處理 API 錯誤回應
                        }
                    }
                    override fun onFailure(call: Call<OperationMsg>, t: Throwable) {
                        Log.d("header ", "RegisterAccountApi call failed")
                    }

                })

            }










            }


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


