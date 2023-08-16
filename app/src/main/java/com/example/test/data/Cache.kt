package com.example.test.data

import android.util.Log

open class Cache<T> {
    protected var content: T? = null
    private var hasExpired: Boolean = true
    fun getContents(): T? {
        return if (hasExpired) {
            null
        } else {
            content
        }
    }

    fun setCache(objects: T) {
        content = objects
        hasExpired = false
    }

    fun expire() {
        Log.d("header ", "cache expired")
        hasExpired = true
    }

    fun hasExpired(): Boolean {
        return hasExpired
    }
}