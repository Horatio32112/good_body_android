package com.example.test.data

import java.util.Timer
import java.util.TimerTask

class ExpirableCache<T> : Cache<T>() {
    private var content: T? = null
    private var hasExpired = true
    private val timer by lazy { Timer() }
    private val clearCacheTask = object : TimerTask() {
        override fun run() {
            expire()
        }
    }

    fun setCache(objects: T, delaySpan: Long) {
        content = objects
        hasExpired = false

        timer.cancel() // 取消之前的定时任务
        timer.schedule(clearCacheTask, delaySpan)
    }
}