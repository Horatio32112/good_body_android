package com.example.test.activity.basics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.activity.interactionOfUsers.FindUserActivity
import com.example.test.activity.myRecords.MySetsRecordsActivity
import com.example.test.activity.myRecords.MyTimeRecordsActivity
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.model.PersonalProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPersonalProfileActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_personal_profile)
        val context = this
        val heightField: EditText = findViewById(R.id.get_my_personal_profile_HeightInputField)
        val weightField: EditText = findViewById(R.id.get_my_personal_profile_WeightInputField)
        val ageField: EditText = findViewById(R.id.get_my_personal_profile_AgeInputField)
        val genderField: EditText = findViewById(R.id.get_my_personal_profile_GenderInputField)

        val updateButton: Button = findViewById(R.id.get_my_personal_profile_UpdateProfileButton)
        val backHomeButton: Button = findViewById(R.id.get_my_personal_profile_BackHomeButton)

        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val account = sharedPreferences.getString("account", "")


        fun setTextByResponse(response: PersonalProfile) {
            heightField.setText(response.height.toString())
            weightField.setText(response.weight.toString())
            ageField.setText(response.age.toString())
            genderField.setText(response.gender)
        }

        val okHttpClient = ApiSetUp.createOkHttpClient()
        val apiBuilder = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
        val getProfileApiCaller = apiBuilder.getProfile(account.toString())
        getProfileApiCaller.enqueue(object : Callback<PersonalProfile> {
            override fun onResponse(
                call: Call<PersonalProfile>,
                response: Response<PersonalProfile>
            ) {
                Log.d("header ", "test ${Thread.currentThread()}")

                if (response.isSuccessful) {
                    //API回傳結果
                    val response = response.body()
                    response?.let { it1 -> setTextByResponse(it1) }

                    Log.d("header ", "$account got his own profile")

                } else {
                    Log.d("header ", "$account failed to get his own profile")
                    // 處理 API 錯誤回應
                }
            }

            override fun onFailure(call: Call<PersonalProfile>, t: Throwable) {
                Log.d("header ", "$account failed to get his own profile")
            }
        })

        updateButton.setOnClickListener {
            val updateProfileApiCaller = apiBuilder.updateProfile(
                account.toString(),
                heightField.text.toString().toInt(),
                weightField.text.toString().toInt(),
                ageField.text.toString().toInt(),
                genderField.text.toString()
            )
            updateProfileApiCaller.enqueue(object : Callback<PersonalProfile> {
                override fun onResponse(
                    call: Call<PersonalProfile>,
                    response: Response<PersonalProfile>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果
                        val response = response.body()
                        response?.let { it1 -> setTextByResponse(it1) }

                        val toast = Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT)
                        toast.show()

                        Log.d("header ", "$account updated his own profile")

                    } else {
                        Log.d("header ", "$account failed to update his own profile")
                        // 處理 API 錯誤回應
                    }
                }

                override fun onFailure(call: Call<PersonalProfile>, t: Throwable) {
                    Log.d("header ", "$account failed to update his own profile")
                }
            })
        }
        backHomeButton.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val context = this
        return when (item.itemId) {
            R.id.menu_FindUser -> {
                val intent = Intent(context, FindUserActivity::class.java)
                context.startActivity(intent)

                true
            }

            R.id.menu_MyProfile -> {
                val intent = Intent(context, MyPersonalProfileActivity::class.java)
                context.startActivity(intent)

                true
            }

            R.id.menu_MySetsRecords -> {
                val intent = Intent(context, MySetsRecordsActivity::class.java)
                context.startActivity(intent)
                true
            }

            R.id.menu_MyTimeRecords -> {
                val intent = Intent(context, MyTimeRecordsActivity::class.java)
                context.startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
