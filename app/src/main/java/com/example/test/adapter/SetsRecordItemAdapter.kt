package com.example.test.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.api.ApiSetUp
import com.example.test.api.ApiV1
import com.example.test.model.OperationMsg
import com.example.test.model.SetsRecord
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetsRecordItemAdapter(private val dataset: List<SetsRecord>) :
    RecyclerView.Adapter<SetsRecordItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentInputField: EditText = view.findViewById(R.id.set_record_ContentInput)
        val setsInputField: EditText = view.findViewById(R.id.set_record_SetsInput)
        val repsInputField: EditText = view.findViewById(R.id.set_record_RepsInput)
        val weightInputField: EditText = view.findViewById(R.id.set_record_WeightInput)
        val updateBtn: Button = view.findViewById(R.id.set_record_UpdateBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.sets_record_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val setsRecord = dataset[position]
        val recordId = setsRecord.record_id
        holder.contentInputField.setText(setsRecord.contents)
        holder.setsInputField.setText(setsRecord.sets.toString())
        holder.repsInputField.setText(setsRecord.reps.toString())
        holder.weightInputField.setText(setsRecord.weight.toString())

        holder.updateBtn.setOnClickListener {
            val content = holder.contentInputField.text.toString()
            val sets = holder.setsInputField.text.toString().toInt()
            val reps = holder.repsInputField.text.toString().toInt()
            val weight = holder.weightInputField.text.toString().toFloat()

            val okHttpClient = ApiSetUp.createOkHttpClient()
            val retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
            val retrofitData1 =
                retrofitBuilder1.updateSetsRecords(recordId, content, sets, reps, weight)
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
                    Log.d("header ", "update_sets_records_api call failed")
                }

            })
        }

    }
}