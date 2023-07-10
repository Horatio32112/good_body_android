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
import com.example.test.adapter.RecommendRecordItemAdapter
import com.example.test.R
import com.example.test.activity.interactionOfUsers.FindUserActivity
import com.example.test.activity.interactionOfUsers.UserProfileActivity
import com.example.test.activity.myRecords.MySetsRecordsActivity
import com.example.test.activity.myRecords.MyTimeRecordsActivity
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

        val recommendAccountText1: TextView = findViewById(R.id.home_recommend_follow_Text1)
        val recommendAccountText2: TextView = findViewById(R.id.home_recommend_follow_Text2)
        val recommendAccountText3: TextView = findViewById(R.id.home_recommend_follow_Text3)
        val checkOutButton1: Button = findViewById(R.id.home_recommend_CheckOutBtn1)
        val checkOutButton2: Button = findViewById(R.id.home_recommend_CheckOutBtn2)
        val checkOutButton3: Button = findViewById(R.id.home_recommend_CheckOutBtn3)
        val context: Context = this

        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", -1)

        var recommendedSetsRecords :List<SetsRecord>?
        var recommendedTimeRecords :List<TimesRecord>?
        var recommendedRecords :List<Any?>?
        val recyclerView = findViewById<RecyclerView>(R.id.home_RecycleView)






        val okHttpClient = ApiSetUp.createOkHttpClient()
        val retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
        val retrofitData1 = retrofitBuilder1.getRecommendFollowers(userId.toString())
        retrofitData1.enqueue(object : Callback<RecommendFollowers> {
            override fun onResponse(
                call: Call<RecommendFollowers>,
                response: Response<RecommendFollowers>
            ) {
                Log.d("header ", "test ${Thread.currentThread()}")

                if (response.isSuccessful) {
                    //API回傳結果
                    val response = response.body()

                    recommendAccountText1.text= response!!.account1
                    recommendAccountText2.text=response.account2
                    recommendAccountText3.text=response.account3
                    checkOutButton1.setOnClickListener{
                        val intent = Intent(context, UserProfileActivity::class.java)
                        intent.putExtra("object_user_account", recommendAccountText1.text)
                        context.startActivity(intent)
                    }
                    checkOutButton2.setOnClickListener{
                        val intent = Intent(context, UserProfileActivity::class.java)
                        intent.putExtra("object_user_account", recommendAccountText2.text)
                        context.startActivity(intent)
                    }
                    checkOutButton3.setOnClickListener{
                        val intent = Intent(context, UserProfileActivity::class.java)
                        intent.putExtra("object_user_account", recommendAccountText3.text)
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
            val setsData =  Datasource().recommendSetsRecords(userId)
            val timeData =  Datasource().recommendTimesRecords(userId)

            recommendedSetsRecords=setsData
            recommendedTimeRecords=timeData
            recommendedRecords = recommendedSetsRecords
            for(timeRecord in recommendedTimeRecords!!){
                recommendedRecords = recommendedRecords?.plus(timeRecord)
            }
            recyclerView.adapter = RecommendRecordItemAdapter(recommendedRecords)
            recyclerView.setHasFixedSize(true)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
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


