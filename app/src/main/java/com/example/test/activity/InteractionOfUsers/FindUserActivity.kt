package com.example.test.activity.InteractionOfUsers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.activity.Basics.HomeActivity
import com.example.test.activity.Basics.MyPersonalProfileActivity
import com.example.test.activity.MyRecords.MyTimeRecordsActivity
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.model.OperationMsg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindUserActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_user)
        val SearchBtn: Button =findViewById(R.id.find_user_SearchBtn)
        val homeBtn: Button =findViewById(R.id.find_user_BackHomeBtn)
        val AccountInputField: TextView =findViewById(R.id.find_user_AccountInput)

        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val account = sharedPreferences.getString("account", "")
        val context = this


        homeBtn.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        SearchBtn.setOnClickListener {
            if(AccountInputField.text.toString()==account){
                val toast = Toast.makeText(context, "you can't search for yourself", Toast.LENGTH_SHORT)
                toast.show()
            }
            else{
            val okHttpClient = ApiSetUp.createOkHttpClient()
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
            var retrofitData1 = retrofitBuilder1.check_user_existence(AccountInputField.text.toString())
            retrofitData1.enqueue(object : Callback<OperationMsg> {
                override fun onResponse(
                    call: Call<OperationMsg>,
                    response: Response<OperationMsg>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果
                        var Msg : String? = response.body()?.Msg

                        if(Msg=="User_exist"){
                            val intent = Intent(context, UserProfileActivity::class.java)
                            intent.putExtra("object_user_account", AccountInputField.text.toString())

                            context.startActivity(intent)

                            Log.d("header ", "user ${AccountInputField.text} exists")
                        }else if(Msg=="User_not_exist"){
                            val toast = Toast.makeText(context, "user not exist", Toast.LENGTH_SHORT)
                            toast.show()
                        }else{
                            val toast = Toast.makeText(context, "unknown problem occurred", Toast.LENGTH_SHORT)
                            toast.show()
                        }



                    } else {
                        Log.d("header ", "Something went wrong")
                        // 處理 API 錯誤回應
                    }
                }
                override fun onFailure(call: Call<OperationMsg>, t: Throwable) {
                    Log.d("header ", "CheckUserExistenceApi call failed")
                }

            })


        }


    }}

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
                val intent = Intent(context, FindUserActivity::class.java)
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