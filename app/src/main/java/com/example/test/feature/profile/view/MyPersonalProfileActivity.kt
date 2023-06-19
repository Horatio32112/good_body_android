package com.example.test.feature.profile.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.HomeActivity
import com.example.test.R
import com.example.test.feature.profile.viewmodel.MyPersonalProfileViewModel

class MyPersonalProfileActivity : AppCompatActivity(){

    private val viewModel by viewModels<MyPersonalProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_personal_profile)
        val context = this
        val height_field: EditText = findViewById(R.id.get_my_personal_profile_HeightInputField)
        val weight_field: EditText = findViewById(R.id.get_my_personal_profile_WeightInputField)
        val age_field: EditText = findViewById(R.id.get_my_personal_profile_AgeInputField)
        val gender_field: EditText = findViewById(R.id.get_my_personal_profile_GenderInputField)

        val update_button: Button = findViewById(R.id.get_my_personal_profile_UpdateProfileButton)
        val back_home_button: Button = findViewById(R.id.get_my_personal_profile_BackHomeButton)

        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val account = sharedPreferences.getString("account", "")

        viewModel.getProfile(account)

        viewModel.profile.observe(this) { data ->
            height_field.setText((data?.height ?: 0).toString())
            weight_field.setText((data?.weight ?: 0).toString())
            age_field.setText((data?.age ?: 0).toString())
            gender_field.setText(data?.gender ?: "unknowned")
        }

        update_button.setOnClickListener {
            viewModel.updateProfile()
        }
        back_home_button.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }


}