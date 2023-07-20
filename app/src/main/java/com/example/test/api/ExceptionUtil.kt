package com.example.test.api



sealed class ApiException(message: String): Exception(message = message) {
    object Read : ApiException("received response but failed to read")
    object Call : ApiException("failed when calling api")
}