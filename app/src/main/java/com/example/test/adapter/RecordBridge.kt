package com.example.test.adapter

import com.example.test.viewmodel.RecordViewModel

class RecordBridge(private val viewModel: RecordViewModel) {

    fun <T>doAction (t: T, action: (t: T,viewModel:RecordViewModel)->Unit){
        action(t,viewModel)

    }
}