package com.example.test.repositories

import android.util.Log
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.model.PersonalProfileData
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AccountRepository {

    private val client = ApiSetUp.createOkHttpClient()

    suspend fun getProfile(): PersonalProfileData? {
        return suspendCancellableCoroutine<PersonalProfileData?> {
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(client)
            var retrofitData1 = retrofitBuilder1.get_profile(account)
            retrofitData1.enqueue(object : Callback<PersonalProfileData> {
                override fun onResponse(
                    call: Call<PersonalProfileData>,
                    response: Response<PersonalProfileData>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果
                        var response = response.body()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "${account} got his own profile")

                    } else {
                        Log.d("header ", "${account} failed to get his own profile")
                        // 處理 API 錯誤回應
                        it.resumeWith(Result.failure(Exception()))
                    }
                }
                override fun onFailure(call: Call<PersonalProfileData>, t: Throwable) {
                    Log.d("header ", "${account} failed to get his own profile")
                    it.resumeWith(Result.failure(Exception()))
                }
            })
        }

    }

    suspend fun updateProfile(): PersonalProfileData? {
        return suspendCancellableCoroutine<PersonalProfileData?> {
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(client)
            var retrofitData1 = retrofitBuilder1.update_profile(
                account.toString(),
                height_field.text.toString().toInt(),
                weight_field.text.toString().toInt(),
                age_field.text.toString().toInt(),
                gender_field.text.toString()
            )
            retrofitData1.enqueue(object : Callback<PersonalProfileData> {
                override fun onResponse(
                    call: Call<PersonalProfileData>,
                    response: Response<PersonalProfileData>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果
                        var response = response.body()
                        it.resumeWith(Result.success(response))


//                    val toast = Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT)
//                    toast.show()

                        Log.d("header ", "${account} updated his own profile")

                    } else {
                        Log.d("header ", "${account} failed to update his own profile")
                        // 處理 API 錯誤回應
                        it.resumeWith(Result.failure(Exception()))
                    }
                }

                override fun onFailure(call: Call<PersonalProfileData>, t: Throwable) {
                    Log.d("header ", "${account} failed to update his own profile")
                    it.resumeWith(Result.failure(Exception()))
                }
            })
        }
    }
}