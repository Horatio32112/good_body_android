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
import com.example.test.api.ApiV1
import com.example.test.model.UserId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val to_register_button: Button = findViewById(R.id.to_register_button)
        val login_button: Button = findViewById(R.id.login_button)
        val button: Button = findViewById(R.id.button2)
        val account_input: EditText = findViewById(R.id.register_account_input_field)
        val password_input: EditText = findViewById(R.id.register_password_input_field)


        button.setOnClickListener {
            val context = button.context
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        to_register_button.setOnClickListener {
            val context = to_register_button.context
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }

        login_button.setOnClickListener {

            val account: String = account_input.getText().toString()
            val password: String = password_input.getText().toString()
            val context: Context = this
            if (TextUtils.isEmpty(account)) {
                account_input.setError("This block cannot be blank")

            } else if (TextUtils.isEmpty(password)) {
                password_input.setError("This block cannot be blank")

            } else {


                val okHttpClient = ApiSetUp.createOkHttpClient()
                var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
                var retrofitData1 = retrofitBuilder1.get_id(account)
                retrofitData1.enqueue(object : Callback<UserId> {
                    override fun onResponse(
                        call: Call<UserId>,
                        response: Response<UserId>
                    ) {
                        Log.d("header ", "test ${Thread.currentThread()}")

                        if (response.isSuccessful) {
                            //API回傳結果
                            var user_id : Int? = response.body()?.user_id

                            val toast = Toast.makeText(context, "info_submitted", Toast.LENGTH_SHORT)
                            toast.show()

                            val sharedPreferences = context.getSharedPreferences("account_info", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("account", account)

                            user_id?.let { it1 -> editor.putInt("user_id", it1) }
                            editor.apply()

                            val intent = Intent(context, HomeActivity::class.java)
                            context.startActivity(intent)

                            Log.d("header ", "user exists, id = ${user_id}")
                            Log.d("header ", "${account} logged in.")

                        } else {
                            Log.d("header ", "user not exist or something went wrong")
                            // 處理 API 錯誤回應
                        }
                    }
                    override fun onFailure(call: Call<UserId>, t: Throwable) {
                        Log.d("header ", "GetUserIdByAccountApi call failed")
                    }

                })



        /**
                val context: Context = this
                val toast = Toast.makeText(this, "info_submitted", Toast.LENGTH_SHORT)
                toast.show()



                val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("account", account)
                editor.apply()
            */
            }
        }
    }


}




