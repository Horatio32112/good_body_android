package com.example.test.api

import com.example.test.model.OperationMsg
import com.example.test.model.PersonalProfile
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
    fun getSetsRecords(
        @Field("account") account: String
    ): Call<List<SetsRecord>>

    @FormUrlEncoded
    @POST("/v1/record_times_get")
    fun getTimesRecords(
        @Field("account") account: String
    ): Call<List<TimesRecord>>

    @FormUrlEncoded
    @POST("/v1/record_sets_create")
    fun createSetsRecords(
        @Field("user_id") user_id: Int,
        @Field("content") content: String,
        @Field("set") set: Int,
        @Field("rep") rep: Int,
        @Field("weight") weight: Float

    ): Call<OperationMsg>

    @FormUrlEncoded
    @POST("/v1/record_time_create")
    fun createTimeRecords(
        @Field("user_id") account: Int,
        @Field("contents") contents: String,
        @Field("duration") duration: Int,
        @Field("distance") distance: Float

    ): Call<OperationMsg>

    @FormUrlEncoded
    @DELETE("/v1/record_sets_delete")
    fun deleteSetsRecords(
        @Field("id") id: Int
    ): Call<OperationMsg>

    @FormUrlEncoded
    @DELETE("/v1/record_time_delete")
    fun deleteTimeRecords(
        @Field("id") id: Int
    ): Call<OperationMsg>

    @FormUrlEncoded
    @POST("/v1/get_id")
    fun getId(
        @Field("account") account: String
    ): Call<UserId>

    @FormUrlEncoded
    @POST("/v1/get_personal_profile")
    fun getProfile(
        @Field("account") account: String
    ): Call<PersonalProfile>

    @FormUrlEncoded
    @POST("/v1/register_account")
    fun registerAccount(

        @Field("email") email: String,
        @Field("password") password: String,
        @Field("account") account: String,
        @Field("phone_number") phone_number: String,
        @Field("name") name: String,
        @Field("age") age: Int,
        @Field("height") height: Int,
        @Field("weight") weight: Int,
        @Field("gender") gender: String

    ): Call<OperationMsg>

    @FormUrlEncoded
    @POST("/v1/update_personal_profile")
    fun updateProfile(
        @Field("account") account: String,
        @Field("height") height: Int,
        @Field("weight") weight: Int,
        @Field("age") age: Int,
        @Field("gender") gender: String
    ): Call<PersonalProfile>

    @FormUrlEncoded
    @POST("/v1/recommend_follower")
    fun getRecommendFollowers(
        @Field("user_id") user_id: String
    ): Call<RecommendFollowers>

    @FormUrlEncoded
    @POST("/v1/recommend_sets_txt")
    fun getRecommendSetsRecords(
        @Field("user_id") user_id: Int
    ): Call<List<SetsRecord>>

    @FormUrlEncoded
    @POST("/v1/recommend_times_txt")
    fun getRecommendTimesRecords(
        @Field("user_id") user_id: Int
    ): Call<List<TimesRecord>>

    @FormUrlEncoded
    @PUT("/v1/record_sets_update")
    fun updateSetsRecords(
        @Field("id") id: Int?,
        @Field("content") content: String,
        @Field("set") set: Int,
        @Field("rep") rep: Int,
        @Field("weight") weight: Float
    ): Call<OperationMsg>

    @FormUrlEncoded
    @PUT("/v1/record_time_update")
    fun updateTimeRecords(
        @Field("id") id: Int?,
        @Field("contents") contents: String,
        @Field("duration") duration: Int,
        @Field("distance") distance: Float
    ): Call<OperationMsg>

    @FormUrlEncoded
    @POST("/v1/check_follow")
    fun checkFollow(
        @Field("subject_user_account") subject_user_account: String?,
        @Field("object_user_account") object_user_account: String
    ): Call<OperationMsg>

    @FormUrlEncoded
    @POST("/v1/follow")
    fun follow(
        @Field("subject_user_account") subject_user_account: String?,
        @Field("object_user_account") object_user_account: String
    ): Call<OperationMsg>

    @FormUrlEncoded
    @POST("/v1/unfollow")
    fun unfollow(
        @Field("subject_user_account") subject_user_account: String?,
        @Field("object_user_account") object_user_account: String
    ): Call<OperationMsg>

    @FormUrlEncoded
    @POST("/v1/check_user_existence")
    fun checkUserExistence(
        @Field("account") account: String
    ): Call<OperationMsg>
}