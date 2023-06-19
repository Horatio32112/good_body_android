package com.example.test.api

import com.example.test.model.PersonalProfileData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface GetPersonalProfileApi {
    @FormUrlEncoded
    @POST("/v1/get_personal_profile")
    fun get_profile(
        @Field("account") account: String
        ): Call<PersonalProfileData>
}

