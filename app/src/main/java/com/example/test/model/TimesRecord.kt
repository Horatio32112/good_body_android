package com.example.test.model

data class TimesRecord(
    override val user_id: Int? = null,
    override val contents: String,
    val duration: Int,
    val distance: Float,
    override val record_id: Int,
    override val account: String? = null,
    override val created_at: String? = null

) : Record
