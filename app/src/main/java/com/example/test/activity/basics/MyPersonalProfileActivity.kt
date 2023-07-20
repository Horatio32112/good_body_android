package com.example.test.activity.basics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.activity.interactions.FindUserActivity
import com.example.test.activity.records.MySetsRecordsActivity
import com.example.test.activity.records.MyTimeRecordsActivity
import com.example.test.viewmodel.MyPersonalProfileViewModel
import com.example.test.model.PersonalProfile

class MyPersonalProfileActivity : AppCompatActivity() {

    private val viewModel by viewModels<MyPersonalProfileViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_personal_profile)
        val context = this
        val heightField: EditText = findViewById(R.id.get_my_personal_profile_HeightInputField)
        val weightField: EditText = findViewById(R.id.get_my_personal_profile_WeightInputField)
        val ageField: EditText = findViewById(R.id.get_my_personal_profile_AgeInputField)
        val genderField: EditText = findViewById(R.id.get_my_personal_profile_GenderInputField)

        val updateButton: Button = findViewById(R.id.get_my_personal_profile_UpdateProfileButton)
        val backHomeButton: Button = findViewById(R.id.get_my_personal_profile_BackHomeButton)

        val sharedPreferences = getSharedPreferences("account_info", Context.MODE_PRIVATE)
        val account = sharedPreferences.getString("account", "")

        viewModel.getProfile(account.toString())

        viewModel.profileLiveData.observe(this) { data ->
            data ?: return@observe
            heightField.setText((data.height).toString())
            weightField.setText((data.weight).toString())
            ageField.setText((data.age).toString())
            genderField.setText(data.gender)
        }


        updateButton.setOnClickListener {
            val profile = PersonalProfile(ageField.text.toString().toInt(),heightField.text.toString().toInt(),weightField.text.toString().toInt(),genderField.text.toString())
            viewModel.updateProfile(account.toString(),profile)

        }
        backHomeButton.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
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
