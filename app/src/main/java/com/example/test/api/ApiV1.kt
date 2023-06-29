package com.example.test.api

import com.example.test.model.OperationMsg
import com.example.test.model.PersonalProfileData
import com.example.test.model.RecommendFollowers
import com.example.test.model.SetsRecord
import com.example.test.model.TimesRecord
import com.example.test.model.UserId
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiV1 {

    @FormUrlEncoded
    @POST("/v1/record_sets_get")
    fun get_sets_records(
        @Field("account") account: String
    ): Call<List<SetsRecord>>

    @FormUrlEncoded
    @POST("/v1/record_times_get")
    fun get_times_records(
        @Field("account") account: String
    ): Call<List<TimesRecord>>

    @FormUrlEncoded
    @POST("/v1/record_sets_create")
    fun create_sets_records(
        @Field("user_id") user_id: Int,
        @Field("content") content: String,
        @Field("set") set: Int,
        @Field("rep") rep: Int,
        @Field("weight") weight: Float

    ): Call<OperationMsg>

    @FormUrlEncoded
    @POST("/v1/record_time_create")
    fun create_time_records(
        @Field("user_id") account: Int,
        @Field("contents") contents: String,
        @Field("duration") duration: Int,
        @Field("distance") distance: Float

    ): Call<OperationMsg>

    @FormUrlEncoded
    @DELETE("/v1/record_sets_delete")
    fun delete_sets_records(
        @Field("id") id: Int
    ): Call<OperationMsg>

    @FormUrlEncoded
    @DELETE("/v1/record_time_delete")
    fun delete_time_records(
        @Field("id") id: Int
    ): Call<OperationMsg>

    @FormUrlEncoded
    @POST("/v1/get_id")
    fun get_id(
        @Field("account") account: String
    ): Call<UserId>

    @FormUrlEncoded
    @POST("/v1/get_personal_profile")
    fun get_profile(
        @Field("account") account: String
    ): Call<PersonalProfileData>

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
    @POST("/v1/recommend_follower")
    fun get_recommend_followers(
        @Field("user_id") user_id: String
    ): Call<RecommendFollowers>

    @FormUrlEncoded
    @POST("/v1/recommend_sets_txt")
    fun get_recommend_sets_records(
        @Field("user_id") user_id: String
    ): Call<List<SetsRecord>>

    @FormUrlEncoded
    @POST("/v1/recommend_times_txt")
    fun get_recommend_times_records(
        @Field("user_id") user_id: String
    ): Call<List<TimesRecord>>

    @FormUrlEncoded
    @PUT("/v1/record_sets_update")
    fun update_sets_records(
        @Field("id") id: Int?,
        @Field("content") content: String,
        @Field("set") set: Int,
        @Field("rep") rep: Int,
        @Field("weight") weight: Float
    ): Call<OperationMsg>

    @FormUrlEncoded
    @PUT("/v1/record_time_update")
    fun update_time_records(
        @Field("id") id: Int?,
        @Field("contents") contents: String,
        @Field("duration") duration: Int,
        @Field("distance") distance: Float
    ): Call<OperationMsg>
}