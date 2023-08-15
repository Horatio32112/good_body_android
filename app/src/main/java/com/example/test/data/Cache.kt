package com.example.test.data

import android.util.Log

open class Cache<T>(
    var content: T? = null,
    var hasExpired: Boolean = true
) {

    fun getContents(): T? {
        return if (hasExpired) {
            null
        } else {
            content
        }
    }

    open fun setCache(objects: T) {
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