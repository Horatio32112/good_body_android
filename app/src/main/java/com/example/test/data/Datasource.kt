package com.example.test.data

import android.util.Log
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.model.OperationMsg
import com.example.test.model.SetsRecord
import com.example.test.model.TimesRecord
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Datasource {
    private val client = ApiSetUp.createOkHttpClient()
    suspend fun loadSetsRecords(account:String): List<SetsRecord>? {
        return suspendCancellableCoroutine<List<SetsRecord>?> {
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(client)
            var retrofitData1 = retrofitBuilder1.get_sets_records(account)
            retrofitData1.enqueue(object : Callback<List<SetsRecord>> {
                override fun onResponse(
                    call: Call<List<SetsRecord>>,
                    response: Response<List<SetsRecord>>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果

                        var response = response.body()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "${account} got his own sets records")

                    } else {
                        Log.d("header ", "${account} failed to get his sets records")
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

    suspend fun loadTimesRecords(account:String): List<TimesRecord>? {
        return suspendCancellableCoroutine<List<TimesRecord>?> {
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(client)
            var retrofitData1 = retrofitBuilder1.get_times_records(account)
            retrofitData1.enqueue(object : Callback<List<TimesRecord>> {
                override fun onResponse(
                    call: Call<List<TimesRecord>>,
                    response: Response<List<TimesRecord>>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果

                        var response = response.body()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "${account} got his own time records")

                    } else {
                        Log.d("header ", "${account} failed to get his time records")
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

    suspend fun RecommedTimesRecords(user_id:Int): List<TimesRecord>? {
        return suspendCancellableCoroutine<List<TimesRecord>?> {
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(client)
            var retrofitData1 = retrofitBuilder1.get_recommend_times_records(user_id)
            retrofitData1.enqueue(object : Callback<List<TimesRecord>> {
                override fun onResponse(
                    call: Call<List<TimesRecord>>,
                    response: Response<List<TimesRecord>>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果

                        var response = response.body()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "user_id = ${user_id} got recommended time records")

                    } else {
                        Log.d("header ", "user_id = ${user_id} failed to get recommended time records")
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

    suspend fun RecommedSetsRecords(user_id:Int): List<SetsRecord>? {
        return suspendCancellableCoroutine<List<SetsRecord>?> {
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(client)
            var retrofitData1 = retrofitBuilder1.get_recommend_sets_records(user_id)
            retrofitData1.enqueue(object : Callback<List<SetsRecord>> {
                override fun onResponse(
                    call: Call<List<SetsRecord>>,
                    response: Response<List<SetsRecord>>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果

                        var response = response.body()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "user_id = ${user_id} got recommended sets records")

                    } else {
                        Log.d("header ", "user_id = ${user_id} failed to get recommended sets records")
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
    suspend fun Follow(SubjectUserAccount:String?,ObjectUserAccount:String):OperationMsg? {
        return suspendCancellableCoroutine<OperationMsg?> {
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(client)
            var retrofitData1 = retrofitBuilder1.follow(SubjectUserAccount,ObjectUserAccount)
            retrofitData1.enqueue(object : Callback<OperationMsg>{
                override fun onResponse(
                    call: Call<OperationMsg>,
                    response: Response<OperationMsg>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果
                        var response = response.body()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "${SubjectUserAccount} followed ${ObjectUserAccount}")

                    } else {
                        Log.d("header ", "${SubjectUserAccount} failed to follow ${ObjectUserAccount}")
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

    suspend fun UnFollow(SubjectUserAccount:String?,ObjectUserAccount:String):OperationMsg? {
        return suspendCancellableCoroutine<OperationMsg?> {
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(client)
            var retrofitData1 = retrofitBuilder1.unfollow(SubjectUserAccount,ObjectUserAccount)
            retrofitData1.enqueue(object : Callback<OperationMsg>{
                override fun onResponse(
                    call: Call<OperationMsg>,
                    response: Response<OperationMsg>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果
                        var response = response.body()
                        it.resumeWith(Result.success(response))

                        Log.d("header ", "${SubjectUserAccount} followed ${ObjectUserAccount}")

                    } else {
                        Log.d("header ", "${SubjectUserAccount} failed to follow ${ObjectUserAccount}")
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