package com.example.test.adapter

interface Bridge<T> {
    val action: (t: T) -> Unit
}
/**
class test {
    class RecordBridgeTest(val viewModel: RecordViewModel): RecordBridge<Record> {
        override fun doAction(t: Record, action: (t: Record, viewModel: RecordViewModel) -> Unit) {

        }
    }

    val intfacceTest = RecordBridgeTest(viewModel = )
}
        */