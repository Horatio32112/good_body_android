package com.example.test.activity.basics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
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
        val registerButton: Button = findViewById(R.id.register_button)
        val toLoginButton: Button = findViewById(R.id.register_to_login_button)
        val accountInput: EditText = findViewById(R.id.register_account_input_field)
        val passwordInput: EditText = findViewById(R.id.register_password_input_field)
        val emailInput: EditText = findViewById(R.id.register_email_input_field)
        val nameInput: EditText = findViewById(R.id.register_name_input_field)
        val heightInput: EditText = findViewById(R.id.register_height_input_field)
        val weightInput: EditText = findViewById(R.id.register_weight_input_field)
        val genderInput: EditText = findViewById(R.id.register_gender_input_field)
        val ageInput: EditText = findViewById(R.id.register_age_input_field)
        val phoneNumberInput: EditText = findViewById(R.id.register_phone_number_input_field)

        toLoginButton.setOnClickListener {
            val context = toLoginButton.context
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }


        registerButton.setOnClickListener {

            val account: String = accountInput.text.toString()
            val password: String = passwordInput.text.toString()
            val name: String = nameInput.text.toString()
            val phoneNumber: String = phoneNumberInput.text.toString()
            val email: String = emailInput.text.toString()
            val gender: String = genderInput.text.toString()
            val height: Int = heightInput.text.toString().toInt()
            val weight: Int = weightInput.text.toString().toInt()
            val age: Int = ageInput.text.toString().toInt()

            if (account.isNullOrEmpty() ||
                name.isNullOrEmpty() ||
                email.isNullOrEmpty() ||
                phoneNumber.isNullOrEmpty() ||
                password.isNullOrEmpty()
            ) {
                TODO("warn about empty blanks")
            } else {
                val okHttpClient = ApiSetUp.createOkHttpClient()
                val apiBuilder = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
                val registerAccountApiCaller = apiBuilder.registerAccount(
                    email,
                    password,
                    account,
                    phoneNumber,
                    name,
                    age,
                    height,
                    weight,
                    gender
                )
                registerAccountApiCaller.enqueue(object : Callback<OperationMsg> {
                    override fun onResponse(
                        call: Call<OperationMsg>,
                        response: Response<OperationMsg>
                    ) {
                        Log.d("header ", "test ${Thread.currentThread()}")

                        if (response.isSuccessful) {
                            //API回傳結果
                            val toast =
                                Toast.makeText(context, response.body()?.Msg, Toast.LENGTH_SHORT)
                            toast.show()

                            val getIdApiCaller = apiBuilder.getId(account)
                            getIdApiCaller.enqueue(object : Callback<UserId> {
                                override fun onResponse(
                                    call: Call<UserId>,
                                    response: Response<UserId>
                                ) {
                                    Log.d("header ", "test ${Thread.currentThread()}")

                                    if (response.isSuccessful) {
                                        //API回傳結果
                                        val userId: Int? = response.body()?.user_id

                                        val sharedPreferences = context.getSharedPreferences(
                                            "account_info",
                                            Context.MODE_PRIVATE
                                        )
                                        val editor = sharedPreferences.edit()
                                        editor.putString("account", account)
                                        userId?.let { it1 -> editor.putInt("user_id", it1) }
                                        editor.apply()

                                        val intent = Intent(context, HomeActivity::class.java)
                                        context.startActivity(intent)

                                        Log.d("header ", "user exists, id = $userId")
                                        Log.d("header ", "$account registered and logged in.")

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



