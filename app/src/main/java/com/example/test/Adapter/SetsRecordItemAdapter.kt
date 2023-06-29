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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetsRecordItemAdapter(private val context: Context, private val dataset: List<SetsRecord>?) : RecyclerView.Adapter<SetsRecordItemAdapter.ItemViewHolder>(){
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val ContentInputField: TextView = view.findViewById(R.id.set_record_ContentInput)
        val SetsInputField: TextView = view.findViewById(R.id.set_record_SetsInput)
        val RepsInputField: TextView = view.findViewById(R.id.set_record_RepsInput)
        val WeightInputField: TextView = view.findViewById(R.id.set_record_WeightInput)
        val UpdateBtn: TextView = view.findViewById(R.id.set_record_UpdateBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.sets_record_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset?.size ?: return 0
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val sets_record = dataset?.get(position)
        val record_id= sets_record?.record_id
        holder.ContentInputField.text = sets_record?.contents
        holder.SetsInputField.text = sets_record?.sets.toString()
        holder.RepsInputField.text = sets_record?.reps.toString()
        holder.WeightInputField.text = sets_record?.weight.toString()

        holder.UpdateBtn.setOnClickListener{
            val content=holder.ContentInputField.text.toString()
            val sets=holder.SetsInputField.getText().toString().toInt()
            val reps=holder.RepsInputField.getText().toString().toInt()
            val weight=holder.WeightInputField.getText().toString().toFloat()

            val okHttpClient = ApiSetUp.createOkHttpClient()
            var retrofitBuilder1 = ApiSetUp.createRetrofit<ApiV1>(okHttpClient)
            var retrofitData1 = retrofitBuilder1.update_sets_records(record_id,content,sets,reps,weight)
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
                    Log.d("header ", "update_sets_records_api call failed")
                }

            })
        }

    }
}