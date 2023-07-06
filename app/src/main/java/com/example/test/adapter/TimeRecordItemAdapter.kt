package com.example.test.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.model.OperationMsg
import com.example.test.model.TimesRecord
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimeRecordItemAdapter(private val dataset: List<TimesRecord>) :
    RecyclerView.Adapter<TimeRecordItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentInputField: TextView = view.findViewById(R.id.time_record_ContentInput)
        val durationInputField: TextView = view.findViewById(R.id.time_record_DurationInput)
        val distanceInputField: TextView = view.findViewById(R.id.time_record_DistanceInput)

        val updateBtn: TextView = view.findViewById(R.id.time_record_UpdateBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_record_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val timeRecord = dataset[position]
        val recordId = timeRecord.record_id
        holder.contentInputField.text = timeRecord.contents
        holder.durationInputField.text = timeRecord.duration.toString()
        holder.distanceInputField.text = timeRecord.distance.toString()

        holder.updateBtn.setOnClickListener {
            val content = holder.contentInputField.text.toString()
            val duration = holder.durationInputField.text.toString().toInt()
            val distance = holder.distanceInputField.text.toString().toFloat()

            val okHttpClient = ApiSetUp.createOkHttpClient()
            val retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
            val retrofitData1 =
                retrofitBuilder1.update_time_records(recordId, content, duration, distance)
            retrofitData1.enqueue(object : Callback<OperationMsg> {
                override fun onResponse(
                    call: Call<OperationMsg>,
                    response: Response<OperationMsg>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果
                        Log.d("header ", "record id $recordId is updated")

                    } else {
                        Log.d("header ", "record id $recordId failed to update")
                        // 處理 API 錯誤回應
                    }
                }

                override fun onFailure(call: Call<OperationMsg>, t: Throwable) {
                    Log.d("header ", "update_time_records_api call failed")
                }

            })
        }

    }
}