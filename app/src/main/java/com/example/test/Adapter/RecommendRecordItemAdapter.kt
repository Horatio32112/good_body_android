package com.example.test.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.SetsRecord
import com.example.test.model.TimesRecord

class RecommendRecordItemAdapter(private val context: Context, private val dataset: List<Any?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    class SetsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val AccountField: TextView = view.findViewById(R.id.recom_sets_AccountText)
        val ContentField: TextView = view.findViewById(R.id.recom_sets_ContentText)
        val SetsField: TextView = view.findViewById(R.id.recom_sets_SetsText)
        val RepsField: TextView = view.findViewById(R.id.recom_sets_RepsText)
        val WeightField: TextView = view.findViewById(R.id.recom_sets_WeightText)
        val TimeField: TextView = view.findViewById(R.id.recom_sets_TimeText)

    }
    class TimeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val AccountField: TextView = view.findViewById(R.id.recommend_time_record_AccountText)
        val ContentField: TextView = view.findViewById(R.id.recommend_time_record_ContentText)
        val DurationField: TextView = view.findViewById(R.id.recommend_time_record_DurationText)
        val DistanceField: TextView = view.findViewById(R.id.recommend_time_record_DistanceText)
        val TimeField: TextView = view.findViewById(R.id.recommend_time_record_TimeText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.recommend_sets_record_list_item -> SetsViewHolder(inflater.inflate(viewType, parent, false))

            R.layout.recommend_time_record_list_item -> TimeViewHolder(inflater.inflate(viewType, parent, false))

            else -> throw IllegalArgumentException("Unsupported layout") // in case populated with a model we don't know how to display.
        }
    }

    override fun getItemCount(): Int {
        return dataset?.size ?: return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val element = dataset?.get(position) // assuming your list is called "elements"
        when (holder) {
            is SetsViewHolder -> {
                val setsRecord = element as SetsRecord
                holder.AccountField.text=setsRecord.account
                holder.ContentField.text=setsRecord.contents
                holder.SetsField.text=setsRecord.sets.toString()
                holder.RepsField.text=setsRecord.reps.toString()
                holder.WeightField.text=setsRecord.weight.toString()
                holder.TimeField.text=setsRecord.created_at
            }

            is TimeViewHolder -> {
                val timeRecord = element as TimesRecord
                holder.AccountField.text=timeRecord.account
                holder.ContentField.text=timeRecord.contents
                holder.DurationField.text=timeRecord.duration.toString()
                holder.DistanceField.text=timeRecord.distance.toString()
                holder.TimeField.text=timeRecord.created_at
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        val element = dataset?.get(position) // assuming your list is called "elements"
        return when (element) {
            is SetsRecord -> R.layout.recommend_sets_record_list_item

            is TimesRecord -> R.layout.recommend_time_record_list_item

            else -> throw IllegalArgumentException("Unsupported type") // in case populated with a model we don't know how to display.
        }
    }
}