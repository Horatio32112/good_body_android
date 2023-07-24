package com.example.test.activity.basics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.model.User
import com.example.test.viewmodel.RegisterViewModel


class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val context: Context = this
        val registerButton: Button = findViewById(R.id.register_button)
        val goToLoginPageButton: Button = findViewById(R.id.register_to_login_button)
        val accountInput: EditText = findViewById(R.id.register_account_input_field)
        val passwordInput: EditText = findViewById(R.id.register_password_input_field)
        val emailInput: EditText = findViewById(R.id.register_email_input_field)
        val nameInput: EditText = findViewById(R.id.register_name_input_field)
        val heightInput: EditText = findViewById(R.id.register_height_input_field)
        val weightInput: EditText = findViewById(R.id.register_weight_input_field)
        val genderInput: EditText = findViewById(R.id.register_gender_input_field)
        val ageInput: EditText = findViewById(R.id.register_age_input_field)
        val phoneNumberInput: EditText = findViewById(R.id.register_phone_number_input_field)

        val sharedPreferences = context.getSharedPreferences(
            "account_info",
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()

        viewModel.idLiveData.observe(this) { userId ->
            userId ?: return@observe
            editor.putInt("user_id", userId)
            editor.apply()

            val toast = Toast.makeText(context, "register succeed $userId", Toast.LENGTH_SHORT)
            toast.show()

            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)

        }

        viewModel.msgLiveData.observe(this) { msg ->
            msg ?: return@observe
            val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            toast.show()
        }

        goToLoginPageButton.setOnClickListener {
            val context = goToLoginPageButton.context
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }


        registerButton.setOnClickListener {
            val account = accountInput.text.toString()
            val password: String = passwordInput.text.toString()
            val name: String = nameInput.text.toString()
            val phoneNumber: String = phoneNumberInput.text.toString()
            val email: String = emailInput.text.toString()
            val gender: String = genderInput.text.toString()
            val height: Int = heightInput.text.toString().toInt()
            val weight: Int = weightInput.text.toString().toInt()
            val age: Int = ageInput.text.toString().toInt()

            if (account.isEmpty() ||
                name.isEmpty() ||
                email.isEmpty() ||
                phoneNumber.isEmpty() ||
                password.isEmpty()
            ) {
                val toast = Toast.makeText(context, "empty blank", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                val user = User(
                    email = email,
                    password,
                    account,
                    phoneNumber,
                    name,
                    age,
                    height,
                    weight,
                    gender
                )

                editor.putString("account", account)
                viewModel.register(user)

            }
        }


    }
}



