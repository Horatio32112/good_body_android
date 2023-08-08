package com.example.test.data

class Cache<T>{
    private var content:T?=null
    private var hasExpired = true

    fun getCache():T?{
        return if(hasExpired){
            null
        }else{
            content
        }
    }

    fun setCache(objects: T){
        content=objects
        hasExpired = false
    }

    fun expire(){
        hasExpired=true
    }

    fun hasExpired():Boolean{
        return hasExpired
    }


}