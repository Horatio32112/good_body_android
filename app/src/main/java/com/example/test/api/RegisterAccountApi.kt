package com.example.test.api

import com.example.test.model.OperationMsg
import com.example.test.model.PersonalProfileData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterAccountApi {
    @FormUrlEncoded
    @POST("/v1/register_account")
    fun register_account(

        @Field("email") email: String,
        @Field("password") password:String,
        @Field("account") account:String,
        @Field("phone_number") phone_number:String,
        @Field("name") name:String,
        @Field("age") age:Int,
        @Field("height") height:Int,
        @Field("weight") weight:Int,
        @Field("gender") gender:String

    ): Call<OperationMsg>
}