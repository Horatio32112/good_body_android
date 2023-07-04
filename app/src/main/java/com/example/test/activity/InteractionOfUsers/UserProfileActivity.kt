package com.example.test.activity.InteractionOfUsers

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
import com.example.test.Adapter.RecommendRecordItemAdapter
import com.example.test.R
import com.example.test.activity.Basics.HomeActivity
import com.example.test.activity.Basics.MyPersonalProfileActivity
import com.example.test.activity.MyRecords.MySetsRecordsActivity
import com.example.test.activity.MyRecords.MyTimeRecordsActivity
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.data.Datasource
import com.example.test.model.OperationMsg
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val context = this
        val ObjectUserAccount = intent?.extras?.getString("object_user_account").toString()
        val AccountField: TextView = findViewById(R.id.user_profile_AccountField)
        val follow_button: Button = findViewById(R.id.user_profile_FollowBtn)
        val back_home_button: Button = findViewById(R.id.user_profile_BackHomeBtn)
        val recyclerView = findViewById<RecyclerView>(R.id.user_profile_RecycleView)
        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val account = sharedPreferences.getString("account", "")
        var Records:List<Any>? = null
        var FollowBtnStatus:String = "null"

        AccountField.text=ObjectUserAccount


        back_home_button.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        fun SetUpFollowBtn(){
            if(FollowBtnStatus=="following") {
                follow_button.text="Unfollow"
                follow_button.setOnClickListener{

                    lifecycleScope.launch {
                        Datasource().UnFollow(account,ObjectUserAccount)
                        FollowBtnStatus="not_following"
                        SetUpFollowBtn()
                        Log.d("header ", "status changed")
                    }

                }
            }else if(FollowBtnStatus=="not_following"){
                follow_button.text="Follow"
                follow_button.setOnClickListener{

                    lifecycleScope.launch {
                        Datasource().Follow(account,ObjectUserAccount)
                        FollowBtnStatus="following"
                        SetUpFollowBtn()
                        Log.d("header ", "status changed")
                    }


                }
            }
        }


        val okHttpClient = ApiSetUp.createOkHttpClient()
        var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
        var retrofitData1 = retrofitBuilder1.check_follow(account,ObjectUserAccount)
        retrofitData1.enqueue(object : Callback<OperationMsg> {
            override fun onResponse(
                call: Call<OperationMsg>,
                response: Response<OperationMsg>
            ) {
                Log.d("header ", "test ${Thread.currentThread()}")

                if (response.isSuccessful) {
                    //API回傳結果
                    var follow_status : String? = response.body()?.Msg
                    Log.d("header ", "${follow_status}")
                    if(follow_status=="following" || follow_status=="not_following"){
                        FollowBtnStatus=follow_status
                        SetUpFollowBtn()
                    }

                    Log.d("header ", "${account} checked follow status of ${ObjectUserAccount}")

                } else {
                    Log.d("header ", "${account} failed to checked follow status of ${ObjectUserAccount}")
                    // 處理 API 錯誤回應
                }
            }
            override fun onFailure(call: Call<OperationMsg>, t: Throwable) {
                Log.d("header ", "CheckFollowApi call failed")
            }

        })

        lifecycleScope.launch {
            val Setsdata =  Datasource().loadSetsRecords(ObjectUserAccount.toString())
            val Timedata =  Datasource().loadTimesRecords(ObjectUserAccount.toString())

            Records = Setsdata
            for(timerecord in Timedata!!){
                Records = Records?.plus(timerecord)
            }
            Log.d("header ", "${Records}")
            recyclerView.adapter = RecommendRecordItemAdapter(context, Records)
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
