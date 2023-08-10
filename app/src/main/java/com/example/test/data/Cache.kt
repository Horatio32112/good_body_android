package com.example.test.data

open class Cache<T>{
    private var content:T?=null
    private var hasExpired = true
    fun getContent():T?{
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

    fun expire(){
        hasExpired=true
    }

    fun hasExpired():Boolean{
        return hasExpired
    }


}