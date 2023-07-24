package com.example.test.data

import android.util.Log
import com.example.test.api.ApiException
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.model.OperationMsg
import com.example.test.model.PersonalProfile
import com.example.test.model.ProfileMessenger
import com.example.test.model.SetsRecord
import com.example.test.model.TimesRecord
import com.example.test.model.User
import com.example.test.model.UserId
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Datasource {
    private val client = ApiSetUp.createOkHttpClient()
    private val apiBuilder = ApiSetUp.createRetrofit<ApiV1>(client)
    suspend fun loadSetsRecords(account: String): List<SetsRecord> {
        return suspendCancellableCoroutine {
            val retrofitData1 = apiBuilder.getSetsRecords(account)
            retrofitData1.enqueue(object : Callback<List<SetsRecord>> {
                override fun onResponse(
                    call: Call<List<SetsRecord>>,
                    response: Response<List<SetsRecord>>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果

                        val response = response.body() ?: listOf()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "$account got his own sets records")

                    } else {
                        Log.d("header ", "$account failed to get his sets records")
                        // 處理 API 錯誤回應
                        it.resumeWith(Result.failure(Exception()))
                    }
                }

                override fun onFailure(call: Call<List<SetsRecord>>, t: Throwable) {
                    Log.d("header ", "GetSetsRecordsApi call failed")
                    it.resumeWith(Result.failure(Exception()))
                }
            })
        }

    }

    @Throws(ApiException::class)
    suspend fun register(user: User): Int? {
        return suspendCancellableCoroutine {
            val registerAccountApiCaller = apiBuilder.registerAccount(user)
            registerAccountApiCaller.enqueue(object : Callback<UserId> {
                override fun onResponse(
                    call: Call<UserId>,
                    response: Response<UserId>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        try {
                            it.resumeWith(Result.success(response.body()?.user_id))
                        }catch (ex: ApiException){
                        }


                    } else {
                        it.resumeWith(Result.failure(ApiException.Read))
                        Log.d("header ", "something went wrong when registering")
                        // 處理 API 錯誤回應
                    }
                }

                override fun onFailure(call: Call<UserId>, t: Throwable) {
                    it.resumeWith(Result.failure(ApiException.Call))
                    Log.d("header ", "RegisterAccountApi call failed")
                }

            })
        }
    }
    @Throws(ApiException::class)
    suspend fun getProfile(account: String): ProfileMessenger {
        return suspendCancellableCoroutine {
            val getProfileApiCaller = apiBuilder.getProfile(account)
            getProfileApiCaller.enqueue(object : Callback<PersonalProfile> {
                override fun onResponse(
                    call: Call<PersonalProfile>,
                    response: Response<PersonalProfile>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果

                        val response = response.body()
                        val messenger = ProfileMessenger(response,null)
                        it.resumeWith(Result.success(messenger))

                        Log.d("header ", "$account got his own profile")

                    } else {
                        it.resumeWith(Result.failure(ApiException.Read))
                        Log.d("header ", "$account failed to get his own profile")
                        // 處理 API 錯誤回應
                    }
                }

                override fun onFailure(call: Call<PersonalProfile>, t: Throwable) {
                    it.resumeWith(Result.failure(ApiException.Call))
                    Log.d("header ", "$account failed to get his own profile")
                }
            })
        }
    }

    @Throws(ApiException::class)
    suspend fun updateProfile(account: String,profile: PersonalProfile): PersonalProfile? {
        return suspendCancellableCoroutine {
            val updateProfileApiCaller = apiBuilder.updateProfile(
                account,
                profile.height,
                profile.weight,
                profile.age,
                profile.gender
            )
            updateProfileApiCaller.enqueue(object : Callback<PersonalProfile> {
                override fun onResponse(
                    call: Call<PersonalProfile>,
                    response: Response<PersonalProfile>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果
                        val response = response.body()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "$account updated his own profile")

                    } else {
                        it.resumeWith(Result.failure(ApiException.Read))
                        Log.d("header ", "$account failed to update his own profile")
                        // 處理 API 錯誤回應
                    }
                }

                override fun onFailure(call: Call<PersonalProfile>, t: Throwable) {
                    it.resumeWith(Result.failure(ApiException.Call))
                    Log.d("header ", "$account failed to update his own profile")
                }
            })
        }
    }
    suspend fun loadTimesRecords(account: String): List<TimesRecord> {
        return suspendCancellableCoroutine {
            val retrofitData1 = apiBuilder.getTimesRecords(account)
            retrofitData1.enqueue(object : Callback<List<TimesRecord>> {
                override fun onResponse(
                    call: Call<List<TimesRecord>>,
                    response: Response<List<TimesRecord>>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果

                        val response = response.body() ?: listOf()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "$account got his own time records")

                    } else {
                        Log.d("header ", "$account failed to get his time records")
                        // 處理 API 錯誤回應
                        it.resumeWith(Result.failure(Exception()))
                    }
                }

                override fun onFailure(call: Call<List<TimesRecord>>, t: Throwable) {
                    Log.d("header ", "GetTimesRecordsApi call failed")
                    it.resumeWith(Result.failure(Exception()))
                }
            })
        }

    }

    suspend fun recommendTimesRecords(user_id: Int): List<TimesRecord> {
        return suspendCancellableCoroutine {
            val retrofitData1 = apiBuilder.getRecommendTimesRecords(user_id)
            retrofitData1.enqueue(object : Callback<List<TimesRecord>> {
                override fun onResponse(
                    call: Call<List<TimesRecord>>,
                    response: Response<List<TimesRecord>>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果

                        val response = response.body() ?: listOf()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "user_id = $user_id got recommended time records")

                    } else {
                        Log.d(
                            "header ",
                            "user_id = $user_id failed to get recommended time records"
                        )
                        // 處理 API 錯誤回應
                        it.resumeWith(Result.failure(Exception()))
                    }
                }

                override fun onFailure(call: Call<List<TimesRecord>>, t: Throwable) {
                    Log.d("header ", "GetRecommendedTimeRecord call failed")
                    it.resumeWith(Result.failure(Exception()))
                }
            })
        }

    }

    suspend fun recommendSetsRecords(user_id: Int): List<SetsRecord> {
        return suspendCancellableCoroutine {
            val retrofitData1 = apiBuilder.getRecommendSetsRecords(user_id)
            retrofitData1.enqueue(object : Callback<List<SetsRecord>> {
                override fun onResponse(
                    call: Call<List<SetsRecord>>,
                    response: Response<List<SetsRecord>>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果

                        val response = response.body() ?: listOf()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "user_id = $user_id got recommended sets records")

                    } else {
                        Log.d(
                            "header ",
                            "user_id = $user_id failed to get recommended sets records"
                        )
                        // 處理 API 錯誤回應
                        it.resumeWith(Result.failure(Exception()))
                    }
                }

                override fun onFailure(call: Call<List<SetsRecord>>, t: Throwable) {
                    Log.d("header ", "GetRecommendedSetsRecord call failed")
                    it.resumeWith(Result.failure(Exception()))
                }
            })
        }
    }

    suspend fun follow(SubjectUserAccount: String?, ObjectUserAccount: String): OperationMsg {
        return suspendCancellableCoroutine {
            val retrofitData1 = apiBuilder.follow(SubjectUserAccount, ObjectUserAccount)
            retrofitData1.enqueue(object : Callback<OperationMsg> {
                override fun onResponse(
                    call: Call<OperationMsg>,
                    response: Response<OperationMsg>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果
                        val response = response.body() ?: OperationMsg("")
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "$SubjectUserAccount followed $ObjectUserAccount")

                    } else {
                        Log.d("header ", "$SubjectUserAccount failed to follow $ObjectUserAccount")
                        // 處理 API 錯誤回應
                        it.resumeWith(Result.failure(Exception()))
                    }
                }

                override fun onFailure(call: Call<OperationMsg>, t: Throwable) {
                    Log.d("header ", "FollowApi call failed")
                    it.resumeWith(Result.failure(Exception()))
                }
            })
        }
    }

    suspend fun unFollow(SubjectUserAccount: String?, ObjectUserAccount: String): OperationMsg? {
        return suspendCancellableCoroutine {
            val retrofitData1 = apiBuilder.unfollow(SubjectUserAccount, ObjectUserAccount)
            retrofitData1.enqueue(object : Callback<OperationMsg> {
                override fun onResponse(
                    call: Call<OperationMsg>,
                    response: Response<OperationMsg>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果
                        val response = response.body()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "$SubjectUserAccount unfollowed $ObjectUserAccount")

                    } else {
                        Log.d("header ", "$SubjectUserAccount failed to follow $ObjectUserAccount")
                        // 處理 API 錯誤回應
                        it.resumeWith(Result.failure(Exception()))
                    }
                }

                override fun onFailure(call: Call<OperationMsg>, t: Throwable) {
                    Log.d("header ", "FollowApi call failed")
                    it.resumeWith(Result.failure(Exception()))
                }
            })
        }
    }
}