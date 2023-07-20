package com.example.test.model

import com.google.gson.annotations.SerializedName

data class OperationMsg(
    @SerializedName("Msg")
    var msg:String,

    ){
}
