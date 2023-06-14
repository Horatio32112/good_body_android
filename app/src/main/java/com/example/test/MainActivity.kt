package com.example.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.model.User
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val to_register_button: Button = findViewById(R.id.to_register_button)
        val login_button: Button = findViewById(R.id.login_button)
        val button: Button = findViewById(R.id.button2)
        val account_input: EditText = findViewById(R.id.account_input_field)
        val password_input: EditText = findViewById(R.id.password_input_field)


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
            if (TextUtils.isEmpty(account)) {
                account_input.setError("This block cannot be blank")

            } else if (TextUtils.isEmpty(password)) {
                password_input.setError("This block cannot be blank")

            } else {

                TODO("connect api and get user")

                TODO("if error, then put error msg")
                TODO("if success, put account and user id into editor")
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




