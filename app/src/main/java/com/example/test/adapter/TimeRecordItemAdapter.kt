package com.example.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.activity.records.MyTimeRecordsActivity
import com.example.test.model.SetsRecord
import com.example.test.model.TimesRecord
import com.example.test.viewmodel.RecordViewModel

class TimeRecordItemAdapter(private val dataset: List<TimesRecord>, private val recordBridge: RecordBridge) :
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

            val action: (record: TimesRecord, viewModel: RecordViewModel) -> Unit =
                { record,viewModel ->
                    viewModel.updateRecord(record)
                }
            recordBridge.doAction(TimesRecord(record_id = recordId, contents=content, duration = duration, distance = distance),action)
        }

    }
}