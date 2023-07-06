package com.example.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.SetsRecord
import com.example.test.model.TimesRecord

class RecommendRecordItemAdapter(private val dataset: List<Any?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class SetsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val accountField: TextView = view.findViewById(R.id.recom_sets_AccountText)
        val contentField: TextView = view.findViewById(R.id.recom_sets_ContentText)
        val setsField: TextView = view.findViewById(R.id.recom_sets_SetsText)
        val repsField: TextView = view.findViewById(R.id.recom_sets_RepsText)
        val weightField: TextView = view.findViewById(R.id.recom_sets_WeightText)
        val timeField: TextView = view.findViewById(R.id.recom_sets_TimeText)

    }

    class TimeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val accountField: TextView = view.findViewById(R.id.recommend_time_record_AccountText)
        val contentField: TextView = view.findViewById(R.id.recommend_time_record_ContentText)
        val durationField: TextView = view.findViewById(R.id.recommend_time_record_DurationText)
        val distanceField: TextView = view.findViewById(R.id.recommend_time_record_DistanceText)
        val timeField: TextView = view.findViewById(R.id.recommend_time_record_TimeText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.recommend_sets_record_list_item -> SetsViewHolder(
                inflater.inflate(
                    viewType,
                    parent,
                    false
                )
            )

            R.layout.recommend_time_record_list_item -> TimeViewHolder(
                inflater.inflate(
                    viewType,
                    parent,
                    false
                )
            )

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
                holder.accountField.text = setsRecord.account
                holder.contentField.text = setsRecord.contents
                holder.setsField.text = setsRecord.sets.toString()
                holder.repsField.text = setsRecord.reps.toString()
                holder.weightField.text = setsRecord.weight.toString()
                holder.timeField.text = setsRecord.created_at
            }

            is TimeViewHolder -> {
                val timeRecord = element as TimesRecord
                holder.accountField.text = timeRecord.account
                holder.contentField.text = timeRecord.contents
                holder.durationField.text = timeRecord.duration.toString()
                holder.distanceField.text = timeRecord.distance.toString()
                holder.timeField.text = timeRecord.created_at
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