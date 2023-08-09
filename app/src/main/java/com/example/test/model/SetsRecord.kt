package com.example.test.model

data class SetsRecord(
    override val user_id: Int? = null,
    override val contents: String,
    val sets: Int,
    val reps: Int,
    val weight: Float,
    override val record_id: Int,
    override val account: String? = null,
    override val created_at: String? = null
) : Record
