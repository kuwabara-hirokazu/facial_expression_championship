package com.example.facialexpressionchampionship.data

import okhttp3.RequestBody

interface RequestBodyCreator {
    fun create(url: String): RequestBody?
}

