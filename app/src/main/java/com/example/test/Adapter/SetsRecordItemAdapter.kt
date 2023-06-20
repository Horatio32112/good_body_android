package com.example.test.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.model.SetsRecord

class SetsRecordItemAdapter(private val context: Context, private val dataset: List<SetsRecord>?) : RecyclerView.Adapter<SetsRecordItemAdapter.ItemViewHolder>(){
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.sets_record_list_item)
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
         holder.textView.text = sets_record?.contents

    }
}