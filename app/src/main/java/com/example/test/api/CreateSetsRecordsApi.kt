package com.example.test.api

import com.example.test.model.OperationMsg
import com.example.test.model.PersonalProfileData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CreateSetsRecordsApi {
    @FormUrlEncoded
    @POST("/v1/record_sets_create")
    fun create_sets_records(
        @Field("user_id") account: Int,
        @Field("contents") contents: String,
        @Field("set") set: Int,
        @Field("rep") rep: Int,
        @Field("weight") weight: Float

    ): Call<OperationMsg>
}