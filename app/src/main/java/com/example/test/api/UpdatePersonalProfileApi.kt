package com.example.test.api

import com.example.test.model.PersonalProfileData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface UpdatePersonalProfileApi {
    @FormUrlEncoded
    @POST("/v1/update_personal_profile")
    fun update_profile(
        @Field("account") account: String,
        @Field("height") height: Int,
        @Field("weight") weight: Int,
        @Field("age") age: Int,
        @Field("gender") gender: String
        ): Call<PersonalProfileData>
}


