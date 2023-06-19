package com.example.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.api.ApiSetUp
import com.example.test.api.GetPersonalProfileApi
import com.example.test.api.UpdatePersonalProfileApi
import com.example.test.model.PersonalProfileData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPersonalProfileActivity : AppCompatActivity(){
    val context = this
    val height_field: EditText = findViewById(R.id.get_my_personal_profile_HeightInputField)
    val weight_field: EditText = findViewById(R.id.get_my_personal_profile_WeightInputField)
    val age_field: EditText = findViewById(R.id.get_my_personal_profile_AgeInputField)
    val gender_field: EditText = findViewById(R.id.get_my_personal_profile_GenderInputField)

    val update_button: Button = findViewById(R.id.get_my_personal_profile_UpdateProfileButton)
    val back_home_button: Button = findViewById(R.id.get_my_personal_profile_BackHomeButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_personal_profile)


        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val account = sharedPreferences.getString("account", "")

        val okHttpClient = ApiSetUp.createOkHttpClient()
        var retrofitBuilder1 = ApiSetUp.createRetrofit<GetPersonalProfileApi>(okHttpClient)
        var retrofitData1 = retrofitBuilder1.get_profile(account.toString())
        retrofitData1.enqueue(object : Callback<PersonalProfileData> {
            override fun onResponse(
                call: Call<PersonalProfileData>,
                response: Response<PersonalProfileData>
            ) {
                Log.d("header ", "test ${Thread.currentThread()}")

                if (response.isSuccessful) {
                    //API回傳結果
                    var response = response.body()
                    height_field.setText(response?.height ?: 0)
                    weight_field.setText(response?.weight ?: 0)
                    age_field.setText(response?.age ?: 0)
                    gender_field.setText(response?.gender ?: "unknowned")


                    Log.d("header ", "${account} got his own profile")

                } else {
                    Log.d("header ", "${account} failed to get his own profile")
                    // 處理 API 錯誤回應
                }
            }
            override fun onFailure(call: Call<PersonalProfileData>, t: Throwable) {
                Log.d("header ", "${account} failed to get his own profile")
            }
        })

        update_button.setOnClickListener {
            val okHttpClient = ApiSetUp.createOkHttpClient()
            var retrofitBuilder1 = ApiSetUp.createRetrofit<UpdatePersonalProfileApi>(okHttpClient)
            var retrofitData1 = retrofitBuilder1.update_profile(account.toString(),height_field.text.toString().toInt(),weight_field.text.toString().toInt(),age_field.text.toString().toInt(),gender_field.text.toString())
            retrofitData1.enqueue(object : Callback<PersonalProfileData> {
                override fun onResponse(
                    call: Call<PersonalProfileData>,
                    response: Response<PersonalProfileData>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果
                        var response = response.body()
                        height_field.setText(response?.height ?: 0)
                        weight_field.setText(response?.weight ?: 0)
                        age_field.setText(response?.age ?: 0)
                        gender_field.setText(response?.gender ?: "unknowned")

                        val toast = Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT)
                        toast.show()

                        Log.d("header ", "${account} updated his own profile")

                    } else {
                        Log.d("header ", "${account} failed to update his own profile")
                        // 處理 API 錯誤回應
                    }
                }
                override fun onFailure(call: Call<PersonalProfileData>, t: Throwable) {
                    Log.d("header ", "${account} failed to update his own profile")
                }
            })
        }
        back_home_button.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }


}