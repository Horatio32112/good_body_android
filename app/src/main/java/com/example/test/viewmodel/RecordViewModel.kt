package com.example.test.viewmodel

import com.example.test.model.SetsRecord
import com.example.test.model.TimesRecord

interface RecordViewModel {
    fun updateRecord(record:SetsRecord){}
    fun updateRecord(record: TimesRecord){}
}