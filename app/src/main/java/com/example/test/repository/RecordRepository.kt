package com.example.test.repository

import android.util.Log
import com.example.test.api.ApiException
import com.example.test.data.AutoExpireCache
import com.example.test.data.Datasource
import com.example.test.model.SetsRecord
import com.example.test.model.TimesRecord
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

object RecordRepository {
    private val setsRecordsCache = AutoExpireCache<List<SetsRecord>>()
    private val timeRecordsCache = AutoExpireCache<List<TimesRecord>>()
    private val timer = Timer()


    private fun setRecordTimer(account: String, isSetsRecord: Boolean) {
        val clearCacheTask = object : TimerTask() {
            override fun run() {
                GlobalScope.launch {
                    if (isSetsRecord) {
                        setsRecordsCache.setAutoExpireCache(
                            Datasource.loadSetsRecords(account),
                            5 * 60 * 1000
                        )
                    } else {
                        timeRecordsCache.setAutoExpireCache(
                            Datasource.loadTimesRecords(account),
                            5 * 60 * 1000
                        )
                    }
                }
            }
        }

        // 设定五分钟后清除缓存
        val delay = 5 * 60 * 1000L // 5 分钟延迟，单位是毫秒
        val period = 5 * 60 * 1000L // 每隔 5 分钟执行一次，单位是毫秒

        // 首次清除缓存的定时任务
        timer.schedule(clearCacheTask, delay, period)
    }

    @Throws(ApiException::class)
    suspend fun loadSetsRecords(account: String, isMyRecord: Boolean): List<SetsRecord> {
        return if (isMyRecord) {
            setsRecordsCache.getContents() ?: try {
                val list = Datasource.loadSetsRecords(account)
                setsRecordsCache.setAutoExpireCache(list, 5 * 60 * 1000)
                setRecordTimer(account, true)
                Log.d("header ", "$list")
                list
            } catch (ex: ApiException) {
                throw ex
            }
        } else {
            Datasource.loadSetsRecords(account)
        }
    }

    suspend fun loadTimesRecords(account: String, isMyRecord: Boolean): List<TimesRecord> {
        return if (isMyRecord) {
            timeRecordsCache.getContents() ?: try {
                val list = Datasource.loadTimesRecords(account)
                timeRecordsCache.setAutoExpireCache(list, 5 * 60 * 1000)
                setRecordTimer(account, false)
                Log.d("header ", "$list")
                list
            } catch (ex: ApiException) {
                throw ex
            }
        } else {
            Datasource.loadTimesRecords(account)
        }
    }

    suspend fun updateSetsRecords(record: SetsRecord) {
        val action: () -> Unit =
            {
                setsRecordsCache.expire()
            }
        Datasource.updateSetsRecords(record, action)
    }

    suspend fun updateTimeRecords(record: TimesRecord) {
        val action: () -> Unit =
            {
                timeRecordsCache.expire()
            }
        Datasource.updateTimeRecords(record, action)
    }

    suspend fun createSetsRecords(
        userId: Int,
        content: String,
        sets: Int,
        reps: Int,
        weight: Float
    ) {
        val action: () -> Unit =
            {
                setsRecordsCache.expire()
            }
        Datasource.createSetsRecords(userId, content, sets, reps, weight, action)
    }

    suspend fun createTimeRecords(userId: Int, content: String, duration: Int, distance: Float) {
        val action: () -> Unit =
            {
                timeRecordsCache.expire()
            }
        Datasource.createTimeRecords(userId, content, duration, distance, action)
    }
}