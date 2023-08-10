package com.example.test.adapter

interface Bridge<T> {
    val action: (t: T) -> Unit
}