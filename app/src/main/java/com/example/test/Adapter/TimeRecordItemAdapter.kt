package com.example.test.Adapter

import android.content.Context
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
import com.example.test.model.SetsRecord
import com.example.test.model.TimesRecord
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimeRecordItemAdapter(private val context: Context, private val dataset: List<TimesRecord>?) : RecyclerView.Adapter<TimeRecordItemAdapter.ItemViewHolder>(){
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val ContentInputField: TextView = view.findViewById(R.id.time_record_ContentInput)
        val DurationInputField: TextView = view.findViewById(R.id.time_record_DurationInput)
        val DistanceInputField: TextView = view.findViewById(R.id.time_record_DistanceInput)

        val UpdateBtn: TextView = view.findViewById(R.id.time_record_UpdateBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_record_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset?.size ?: return 0
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val time_record = dataset?.get(position)
        val record_id= time_record?.record_id
        holder.ContentInputField.text = time_record?.contents
        holder.DurationInputField.text = time_record?.duration.toString()
        holder.DistanceInputField.text = time_record?.distance.toString()

        holder.UpdateBtn.setOnClickListener{
            val content=holder.ContentInputField.text.toString()
            val duration=holder.DurationInputField.getText().toString().toInt()
            val distance=holder.DistanceInputField.getText().toString().toFloat()

            val okHttpClient = ApiSetUp.createOkHttpClient()
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
            var retrofitData1 = retrofitBuilder1.update_time_records(record_id,content,duration,distance)
            retrofitData1.enqueue(object : Callback<OperationMsg> {
                override fun onResponse(
                    call: Call<OperationMsg>,
                    response: Response<OperationMsg>
                ) {
                    Log.d("header ", "test ${Thread.currentThread()}")

                    if (response.isSuccessful) {
                        //API回傳結果


                        Log.d("header ", "record id ${record_id} is updated")

                    } else {
                        Log.d("header ", "record id ${record_id} failed to update")
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