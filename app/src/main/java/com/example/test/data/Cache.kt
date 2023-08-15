package com.example.test.data

import android.util.Log

open class Cache<T>{
    private var content:T?=null
    private var hasExpired = true
    open fun getContent():T?{
        return if(hasExpired){
            null
        }else{
            content
        }
    }

    open fun setCache(objects: T){
        content=objects
        hasExpired = false
    }

    open fun expire(){
        Log.d("header ", "cache expired")
        hasExpired=true
    }

    fun hasExpired():Boolean{
        return hasExpired
    }


}