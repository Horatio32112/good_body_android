package com.example.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.SetsRecord

class SetsRecordItemAdapter(
    private val dataset: List<SetsRecord>,
    private val bridge: Bridge<SetsRecord>
) :

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

            bridge.action(
                SetsRecord(
                    record_id = recordId,
                    contents = content,
                    sets = sets,
                    reps = reps,
                    weight = weight
                )
            )

        }

    }
}