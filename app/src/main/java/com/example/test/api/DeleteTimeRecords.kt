package com.example.test.api

import com.example.test.model.OperationMsg
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface DeleteTimeRecords {
    @FormUrlEncoded
    @DELETE("/v1/record_time_delete")
    fun delete_time_records(
        @Field("id") id: Int
    ): Call<OperationMsg>
}