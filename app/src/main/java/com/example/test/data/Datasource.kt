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
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Datasource {
    private val client: OkHttpClient = ApiSetUp.createOkHttpClient()
    private val apiBuilder: ApiV1 = ApiSetUp.createRetrofit(client)
    private val setsRecordsCache = Cache<List<SetsRecord>>()
    private val timeRecordsCache = Cache<List<TimesRecord>>()
    suspend fun loadSetsRecords(account: String): List<SetsRecord> {
        val records = setsRecordsCache.getContent()
        if (records == null) {
            val action: (response: Response<List<SetsRecord>>) -> Result<List<SetsRecord>> =
                { response ->
                    val body = response.body()
                    if (response.isSuccessful) {
                        //API回傳結果
                        if (body == null) {
                            setsRecordsCache.setCache(listOf())
                            Result.success(listOf())
                        } else {
                            setsRecordsCache.setCache(body)
                            Result.success(body)
                        }
                    } else {
                        Result.failure(ApiException.Read)
                        // 處理 API 錯誤回應
                    }
                }
            return callApi({ apiBuilder.getSetsRecords(account) }, action)
        } else {
            return records
        }
    }


    @Throws(ApiException::class)
    suspend fun register(user: User): Int {
        val action: (response: Response<UserId>) -> Result<Int> =
            { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Result.success(body.user_id)
                } else {
                    Result.failure(ApiException.Read)
                    // 處理 API 錯誤回應
                }
            }
        return callApi({ apiBuilder.registerAccount(user) }, action)
    }

    @Throws(ApiException::class)
    suspend fun getProfile(account: String): ProfileMessenger {
        val action: (response: Response<PersonalProfile>) -> Result<ProfileMessenger> =
            { response ->
                if (response.isSuccessful) {
                    val body = response.body()
                    val messenger = ProfileMessenger(body, null)
                    Result.success(messenger)
                } else {
                    Result.failure(ApiException.Read)
                    // 處理 API 錯誤回應
                }
            }
        return callApi({ apiBuilder.getProfile(account) }, action)
    }

    @Throws(ApiException::class)
    suspend fun updateProfile(account: String, profile: PersonalProfile): PersonalProfile {
        val action: (Response<PersonalProfile>) -> Result<PersonalProfile> = { response ->
            val body = response.body()
            if (response.isSuccessful && body != null) {
                //API回傳結果
                Result.success(body)
            } else {
                Result.failure(ApiException.Read)
                // 處理 API 錯誤回應
            }
        }

        return callApi({
            apiBuilder.updateProfile(
                account,
                profile.height,
                profile.weight,
                profile.age,
                profile.gender
            )
        }, action)
    }

    private fun <T, R> callAction(
        response: Response<T>,
        action: (response: Response<T>) -> Result<R>
    ): Result<R> {
        return action(response)
    }

    private suspend fun <T, R> callApi(
        initApiCaller: () -> Call<T>,
        parseResponse: (
            response: Response<T>
        ) -> Result<R>
    ): R {
        return suspendCancellableCoroutine {
            val apiCaller = initApiCaller()
            enqueueApi(apiCaller, it, parseResponse)
        }
    }

    private fun <T, R> enqueueApi(
        apiCaller: Call<T>,
        it: CancellableContinuation<R>,
        parseResponse: (response: Response<T>) -> Result<R>
    ) {
        apiCaller.enqueue(object : Callback<T> {
            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                Log.d("header ", "test ${Thread.currentThread()}")
                it.resumeWith(callAction(response, parseResponse))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.d("header ", "Api call failed")
                it.resumeWith(Result.failure(Exception()))
            }
        })
    }


    suspend fun loadTimesRecords(account: String): List<TimesRecord> {
        val records = timeRecordsCache.getContent()
        if (records == null) {
            val action: (response: Response<List<TimesRecord>>) -> Result<List<TimesRecord>> =
                { response ->
                    val body = response.body()
                    if (response.isSuccessful) {
                        //API回傳結果
                        if (body == null) {
                            timeRecordsCache.setCache(listOf())
                            Result.success(listOf())
                        } else {
                            timeRecordsCache.setCache(body)
                            Result.success(body)
                        }

                    } else {
                        Result.failure(ApiException.Read)
                        // 處理 API 錯誤回應
                    }
                }
            return callApi({ apiBuilder.getTimesRecords(account) }, action)
        } else {
            return records

        }
    }

    suspend fun recommendTimesRecords(user_id: Int): List<TimesRecord> {
        val action: (response: Response<List<TimesRecord>>) -> Result<List<TimesRecord>> =
            { response ->
                val body = response.body()
                if (response.isSuccessful) {
                    //API回傳結果
                    if (body == null) {
                        Result.success(listOf())
                    } else {
                        Result.success(body)
                    }

                } else {
                    Result.failure(ApiException.Read)
                    // 處理 API 錯誤回應
                }
            }
        return callApi({ apiBuilder.getRecommendTimesRecords(user_id) }, action)
    }

    suspend fun recommendSetsRecords(user_id: Int): List<SetsRecord> {

        val action: (response: Response<List<SetsRecord>>) -> Result<List<SetsRecord>> =
            { response ->
                val body = response.body()
                if (response.isSuccessful) {
                    //API回傳結果
                    if (body == null) {
                        Result.success(listOf())
                    } else {
                        Result.success(body)
                    }

                } else {
                    Result.failure(ApiException.Read)
                    // 處理 API 錯誤回應
                }
            }
        return callApi({ apiBuilder.getRecommendSetsRecords(user_id) }, action)

    }

    suspend fun follow(SubjectUserAccount: String?, ObjectUserAccount: String) {
        val action: (response: Response<OperationMsg>) -> Result<Unit> =
            { response ->
                if (response.isSuccessful) {
                    //API回傳結果
                    Result.success(Unit)
                } else {
                    Result.failure(ApiException.Read)
                    // 處理 API 錯誤回應
                }
            }
        return callApi({ apiBuilder.follow(SubjectUserAccount, ObjectUserAccount) }, action)
    }

    suspend fun unFollow(SubjectUserAccount: String?, ObjectUserAccount: String): OperationMsg {

        val action: (response: Response<OperationMsg>) -> Result<OperationMsg> =
            { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    //API回傳結果
                    Result.success(body)
                } else {
                    Result.failure(ApiException.Read)
                    // 處理 API 錯誤回應
                }
            }
        return callApi({ apiBuilder.unfollow(SubjectUserAccount, ObjectUserAccount) }, action)
    }

    suspend fun getId(account: String): UserId {

        val action: (response: Response<UserId>) -> Result<UserId> =
            { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    //API回傳結果
                    Result.success(body)
                } else {
                    Result.failure(ApiException.Read)
                    // 處理 API 錯誤回應
                }
            }
        return callApi({ apiBuilder.getId(account) }, action)
    }

    suspend fun updateSetsRecords(
        recordId: Int,
        content: String,
        sets: Int,
        reps: Int,
        weight: Float
    ) {

        val action: (response: Response<OperationMsg>) -> Result<Unit> =
            { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    //API回傳結果
                    setsRecordsCache.expire()
                    Result.success(Unit)
                } else {
                    Result.failure(ApiException.Read)
                    // 處理 API 錯誤回應
                }
            }
        return callApi(
            { apiBuilder.updateSetsRecords(recordId, content, sets, reps, weight) },
            action
        )
    }

    suspend fun updateTimeRecords(
        recordId: Int,
        content: String,
        duration: Int,
        distance: Float
    ) {

        val action: (response: Response<OperationMsg>) -> Result<Unit> =
            { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    //API回傳結果
                    timeRecordsCache.expire()
                    Result.success(Unit)
                } else {
                    Result.failure(ApiException.Read)
                    // 處理 API 錯誤回應
                }
            }
        return callApi(
            { apiBuilder.updateTimeRecords(recordId, content, duration, distance) },
            action
        )
    }

    suspend fun createTimeRecords(
        userId: Int, content: String, duration: Int, distance: Float
    ) {

        val action: (response: Response<OperationMsg>) -> Result<Unit> =
            { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    //API回傳結果
                    timeRecordsCache.expire()
                    Result.success(Unit)
                } else {
                    Result.failure(ApiException.Read)
                    // 處理 API 錯誤回應
                }
            }
        return callApi(
            { apiBuilder.createTimeRecords(userId, content, duration, distance) },
            action
        )
    }

    suspend fun createSetsRecords(
        userId: Int, content: String, sets: Int, reps: Int, weight: Float
    ) {

        val action: (response: Response<OperationMsg>) -> Result<Unit> =
            { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    //API回傳結果
                    setsRecordsCache.expire()
                    Result.success(Unit)
                } else {
                    Result.failure(ApiException.Read)
                    // 處理 API 錯誤回應
                }
            }
        return callApi(
            { apiBuilder.createSetsRecords(userId, content, sets, reps, weight) },
            action
        )
    }

}