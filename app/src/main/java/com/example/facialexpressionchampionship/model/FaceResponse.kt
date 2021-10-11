package com.example.facialexpressionchampionship.model

import com.google.gson.annotations.SerializedName

data class FaceResponse(
    @SerializedName("faceId")
    val faceId: String,
    @SerializedName("faceAttributes")
    val faceAttributes: FaceAttributes
)

data class FaceAttributes(
    val emotion: Emotion
)
