package com.example.test.model

data class SetsRecord(
    override val user_id: Int,
    override val contents: String,
    val sets: Int,
    val reps: Int,
    val weight: Int,
    override val record_id: Int,
    override val account: String,
    override val created_at: String
) : Record
