package com.example.test.data

import java.util.Timer
import java.util.TimerTask

class Cache<T>{
    private var content:T?=null
    private var hasExpired = true
    private val timer = Timer()
    fun getContent():T?{
        return if(hasExpired){
            null
        }else{
            content
        }
    }

    fun setCache(objects: T){
        content=objects
        hasExpired = false

        val clearCacheTask = object : TimerTask() {
            override fun run() {
                expire()
            }
        }

        // 设定五分钟后清除缓存
        val delay = 5 * 59 * 1000L // 5 分钟延迟，单位是毫秒
        timer.schedule(clearCacheTask, delay)
    }

    fun expire(){
        hasExpired=true
    }

    fun hasExpired():Boolean{
        return hasExpired
    }


}