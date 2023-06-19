package com.example.test.api

import com.example.test.model.OperationMsg
import com.example.test.model.PersonalProfileData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CreateTimesRecordsApi {
    @FormUrlEncoded
    @POST("/v1/record_time_create")
    fun create_time_records(
        @Field("user_id") account: Int,
        @Field("contents") contents: String,
        @Field("duration") duration: Int,
        @Field("distance") distance: Float

    ): Call<OperationMsg>
}