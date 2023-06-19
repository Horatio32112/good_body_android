package com.example.test.api

import com.example.test.model.SetsRecord
import com.example.test.model.TimesRecord
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GetTimesRecordsApi {
    @FormUrlEncoded
    @POST("/v1/record_times_get")
    fun get_times_records(
        @Field("account") account: String
    ): Call<List<TimesRecord>>
}