package com.example.test.api

import com.example.test.model.PersonalProfileData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiV1 {
    @FormUrlEncoded
    @POST("/v1/update_personal_profile")
    fun update_profile(
        @Field("account") account: String,
        @Field("height") height: Int,
        @Field("weight") weight: Int,
        @Field("age") age: Int,
        @Field("gender") gender: String
    ): Call<PersonalProfileData>

    @FormUrlEncoded
    @POST("/v1/get_personal_profile")
    fun get_profile(
        @Field("account") account: String
    ): Call<PersonalProfileData>
}