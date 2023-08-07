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
import androidx.lifecycle.lifecycleScope
import com.example.test.R
import com.example.test.api.ApiException
import com.example.test.data.Datasource
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val goToRegisterPageButton: Button = findViewById(R.id.to_register_button)
        val loginButton: Button = findViewById(R.id.login_button)
        val button: Button = findViewById(R.id.button2)
        val accountInput: EditText = findViewById(R.id.register_account_input_field)
        val passwordInput: EditText = findViewById(R.id.register_password_input_field)


        button.setOnClickListener {
            val context = button.context
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        goToRegisterPageButton.setOnClickListener {
            val context = goToRegisterPageButton.context
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }

        loginButton.setOnClickListener {

            val account: String = accountInput.text.toString()
            val password: String = passwordInput.text.toString()
            val context: Context = this
            if (TextUtils.isEmpty(account)) {
                accountInput.error="This block cannot be blank"

            } else if (TextUtils.isEmpty(password)) {
                passwordInput.error="This block cannot be blank"

            } else {
                lifecycleScope.launch {
                    try {
                        val userId = Datasource.getId(account).user_id
                        val toast = Toast.makeText(context, "login_info_submitted", Toast.LENGTH_SHORT)
                        toast.show()

                        val sharedPreferences = context.getSharedPreferences("account_info", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("account", account)

                        userId.let { id -> editor.putInt("user_id", id) }
                        editor.apply()

                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)

                        Log.d("header ", "user exists, id = $userId")
                        Log.d("header ", "$account logged in.")

                    } catch (ex: ApiException) {
                        val toast = Toast.makeText(context, "login_failed", Toast.LENGTH_SHORT)
                        toast.show()
                    }

                }
            }
        }
    }


}




