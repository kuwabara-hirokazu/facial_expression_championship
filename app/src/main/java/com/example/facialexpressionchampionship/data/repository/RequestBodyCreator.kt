package com.example.facialexpressionchampionship.data.repository

import okhttp3.RequestBody

interface RequestBodyCreator {
    fun create(url: String): RequestBody?
}
