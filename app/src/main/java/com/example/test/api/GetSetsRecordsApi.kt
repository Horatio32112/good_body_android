package com.example.test.api

import com.example.test.model.PersonalProfileData
import com.example.test.model.SetsRecord
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GetSetsRecordsApi {
    @FormUrlEncoded
    @POST("/v1/record_sets_get")
    fun get_sets_records(
        @Field("account") account: String
    ): Call<List<SetsRecord>>
}