package com.example.test.data

import android.util.Log
import java.util.Timer
import java.util.TimerTask

class AutoExpireCache<T> : Cache<T>() {
    private var timer = Timer()

    fun setAutoExpireCache(cacheContent: T, delaySpan: Long) {
        setCache(cacheContent)
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