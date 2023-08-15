package com.example.test.data

import android.util.Log
import java.util.Timer
import java.util.TimerTask

class ExpirableCache<T> : Cache<T>() {
    private var content: T? = null
    private var hasExpired = true
    private var timer = Timer()

    override fun getContent():T?{
        return if(hasExpired){
            null
        }else{
            content
        }
    }

    fun setCache(cacheContent: T, delaySpan: Long) {
        content = cacheContent
        hasExpired = false
        Log.d("header ", "cache set with content=$content")

        timer.cancel() // 取消之前的定时任务
        timer = Timer()
        val clearCacheTask = object : TimerTask() {
            override fun run() {
                expire()
            }
        }
        timer.schedule(clearCacheTask, delaySpan)
    }

    override fun expire(){
        Log.d("header ", "cache expired")
        hasExpired=true
    }
}