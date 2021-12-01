package com.example.facialexpressionchampionship.data.repository

import com.example.facialexpressionchampionship.extension.toByteArray
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RequestBodyCreatorImpl : RequestBodyCreator {
    companion object {
        private const val MEDEA_TYPE = "application/octet-stream"
    }

    override fun create(url: String): RequestBody? {
        val byte = File(url)
            .toByteArray() ?: return null

        return byte.toRequestBody(MEDEA_TYPE.toMediaTypeOrNull(), 0, byte.size)
    }
}

