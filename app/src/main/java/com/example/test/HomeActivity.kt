package com.example.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.model.RecommendFollowers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recommend_account_text1: TextView = findViewById(R.id.home_recommend_follow_Text1)
        val recommend_account_text2: TextView = findViewById(R.id.home_recommend_follow_Text2)
        val recommend_account_text3: TextView = findViewById(R.id.home_recommend_follow_Text3)
        val check_out_button1: Button = findViewById(R.id.home_recommend_CheckOutBtn1)
        val check_out_button2: Button = findViewById(R.id.home_recommend_CheckOutBtn2)
        val check_out_button3: Button = findViewById(R.id.home_recommend_CheckOutBtn3)
        val context: Context = this

        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val user_id = sharedPreferences.getInt("user_id", -1)


        val okHttpClient = ApiSetUp.createOkHttpClient()
        var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
        var retrofitData1 = retrofitBuilder1.get_recommend_followers(user_id.toString())
        retrofitData1.enqueue(object : Callback<RecommendFollowers> {
            override fun onResponse(
                call: Call<RecommendFollowers>,
                response: Response<RecommendFollowers>
            ) {
                Log.d("header ", "test ${Thread.currentThread()}")

                if (response.isSuccessful) {
                    //API回傳結果
                    var response = response.body()

                    recommend_account_text1.text= response!!.account1
                    recommend_account_text2.text=response.account2
                    recommend_account_text3.text=response.account3
                    check_out_button1.setOnClickListener{
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                    }
                    check_out_button2.setOnClickListener{
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                    }
                    check_out_button3.setOnClickListener{
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                    }


                    Log.d("header ", "Got recommend followers for id=${user_id}")

                } else {
                    Log.d("header ", "something went wrong when calling recommend followers api")
                    Log.d("header ", "id=${user_id}")
                    Log.d("header ", "${response}")
                    // 處理 API 錯誤回應
                }
            }
            override fun onFailure(call: Call<RecommendFollowers>, t: Throwable) {
                Log.d("header ", "recommend followers api call failed")
            }

        })

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val context = this
        return when (item.itemId) {
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