package com.example.test.activity.interactions

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
import com.example.test.activity.basics.HomeActivity
import com.example.test.activity.basics.MyPersonalProfileActivity
import com.example.test.activity.records.MySetsRecordsActivity
import com.example.test.activity.records.MyTimeRecordsActivity
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.data.Datasource
import com.example.test.model.OperationMsg
import com.example.test.model.Record
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherUserProfileActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val context = this
        val objectUserAccount = intent?.extras?.getString("object_user_account").toString()
        val accountField: TextView = findViewById(R.id.user_profile_AccountField)
        val followButton: Button = findViewById(R.id.user_profile_FollowBtn)
        val backHomeButton: Button = findViewById(R.id.user_profile_BackHomeBtn)
        val recyclerView = findViewById<RecyclerView>(R.id.user_profile_RecycleView)
        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val account = sharedPreferences.getString("account", "")
        var records: List<Record>?
        var followBtnStatus = "null"

        accountField.text = objectUserAccount


        backHomeButton.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        fun setUpFollowBtn() {
            if (followBtnStatus == "following") {
                followButton.text = "Unfollow"
                followButton.setOnClickListener {

                    lifecycleScope.launch {
                        Datasource().unFollow(account, objectUserAccount)
                        followBtnStatus = "not_following"
                        setUpFollowBtn()
                        Log.d("header ", "status changed")
                    }

                }
            } else if (followBtnStatus == "not_following") {
                followButton.text = "Follow"
                followButton.setOnClickListener {

                    lifecycleScope.launch {
                        Datasource().follow(account, objectUserAccount)
                        followBtnStatus = "following"
                        setUpFollowBtn()
                        Log.d("header ", "status changed")
                    }


                }
            }
        }


        val okHttpClient = ApiSetUp.createOkHttpClient()
        val apiBuilder = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
        val apiCaller = apiBuilder.checkFollow(account, objectUserAccount)
        apiCaller.enqueue(object : Callback<OperationMsg> {
            override fun onResponse(
                call: Call<OperationMsg>,
                response: Response<OperationMsg>
            ) {
                Log.d("header ", "test ${Thread.currentThread()}")

                if (response.isSuccessful) {
                    //API回傳結果
                    val followStatus: String? = response.body()?.Msg
                    Log.d("header ", "$followStatus")
                    if (followStatus == "following" || followStatus == "not_following") {
                        followBtnStatus = followStatus
                        setUpFollowBtn()
                    }

                    Log.d("header ", "$account checked follow status of $objectUserAccount")

                } else {
                    Log.d(
                        "header ",
                        "$account failed to checked follow status of $objectUserAccount"
                    )
                    // 處理 API 錯誤回應
                }
            }

            override fun onFailure(call: Call<OperationMsg>, t: Throwable) {
                Log.d("header ", "CheckFollowApi call failed")
            }

        })

        lifecycleScope.launch {
            val setsData = Datasource().loadSetsRecords(objectUserAccount)
            val timeData = Datasource().loadTimesRecords(objectUserAccount)

            records = setsData
            timeData.forEach{records = records?.plus(it)}

            Log.d("header ", "$records")
            recyclerView.adapter = RecommendRecordItemAdapter(records)
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
