package com.example.test.activity.basics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.activity.interactions.FindUserActivity
import com.example.test.activity.interactions.OtherUserProfileActivity
import com.example.test.activity.records.MySetsRecordsActivity
import com.example.test.activity.records.MyTimeRecordsActivity
import com.example.test.adapter.RecommendRecordItemAdapter
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.data.Datasource
import com.example.test.model.RecommendFollowers
import com.example.test.model.SetsRecord
import com.example.test.model.TimesRecord
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val firstRecommendAccountText: TextView = findViewById(R.id.home_recommend_follow_FirstText)
        val secondRecommendAccountText: TextView =
            findViewById(R.id.home_recommend_follow_SecondText)
        val thirdRecommendAccountText: TextView = findViewById(R.id.home_recommend_follow_ThirdText)
        val checkOutButton1: Button = findViewById(R.id.home_recommend_FirstCheckOutBtn)
        val checkOutButton2: Button = findViewById(R.id.home_recommend_SecondCheckOutBtn)
        val checkOutButton3: Button = findViewById(R.id.home_recommend_ThirdCheckOutBtn)
        val context: Context = this

        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", -1)

        var recommendedRecords: List<Any?>?
        val recyclerView = findViewById<RecyclerView>(R.id.home_RecycleView)


        val okHttpClient = ApiSetUp.createOkHttpClient()
        val apiBuilder = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
        val apiCaller = apiBuilder.getRecommendFollowers(userId.toString())
        apiCaller.enqueue(object : Callback<RecommendFollowers> {
            override fun onResponse(
                call: Call<RecommendFollowers>,
                response: Response<RecommendFollowers>
            ) {
                Log.d("header ", "test ${Thread.currentThread()}")

                if (response.isSuccessful) {
                    //API回傳結果
                    val response = response.body()

                    firstRecommendAccountText.text = response!!.account1
                    secondRecommendAccountText.text = response.account2
                    thirdRecommendAccountText.text = response.account3
                    checkOutButton1.setOnClickListener {
                        val intent = Intent(context, OtherUserProfileActivity::class.java)
                        intent.putExtra("object_user_account", firstRecommendAccountText.text)
                        context.startActivity(intent)
                    }
                    checkOutButton2.setOnClickListener {
                        val intent = Intent(context, OtherUserProfileActivity::class.java)
                        intent.putExtra("object_user_account", secondRecommendAccountText.text)
                        context.startActivity(intent)
                    }
                    checkOutButton3.setOnClickListener {
                        val intent = Intent(context, OtherUserProfileActivity::class.java)
                        intent.putExtra("object_user_account", thirdRecommendAccountText.text)
                        context.startActivity(intent)
                    }

                    Log.d("header ", "Got recommend followers for id=${userId}")

                } else {
                    Log.d("header ", "something went wrong when calling recommend followers api")
                    Log.d("header ", "id=${userId}")
                    Log.d("header ", "$response")
                    // 處理 API 錯誤回應
                }
            }

            override fun onFailure(call: Call<RecommendFollowers>, t: Throwable) {
                Log.d("header ", "recommend followers api call failed")
            }

        })

        lifecycleScope.launch {
            val setsData = Datasource().recommendSetsRecords(userId)
            val timeData = Datasource().recommendTimesRecords(userId)

            val recommendedSetsRecords = mutableListOf<SetsRecord>()
            recommendedSetsRecords.clear()
            recommendedSetsRecords.addAll(setsData)

            val recommendedTimeRecords = mutableListOf<TimesRecord>()
            recommendedTimeRecords.clear()
            recommendedTimeRecords.addAll(timeData)

            recommendedRecords = recommendedSetsRecords

            recommendedTimeRecords.forEach{recommendedRecords = recommendedRecords?.plus(it)}

            recyclerView.adapter = RecommendRecordItemAdapter(recommendedRecords)
            recyclerView.setHasFixedSize(true)
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


