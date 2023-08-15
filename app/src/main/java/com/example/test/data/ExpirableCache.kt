package com.example.test.data

import android.util.Log
import java.util.Timer
import java.util.TimerTask

class ExpirableCache<T>(content:T?=null,hasExpired:Boolean=true) : Cache<T>(content, hasExpired) {
    private var timer = Timer()


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

}