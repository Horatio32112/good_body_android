package com.example.test.api

import com.example.test.model.PersonalProfileData
import com.example.test.model.UserId
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface GetUserIdByAccountApi {
    @FormUrlEncoded
    @POST("/v1/get_id")
    fun get_id(
        @Field("account") account: String
    ): Call<UserId>
}

