package com.example.facialexpressionchampionship

import com.google.gson.annotations.SerializedName
import java.util.*

data class FaceResponse (
    val results: Results
)

data class Results(
    @SerializedName("faceId")
    val faceId: String,
    @SerializedName("faceAttributes")
    val faceAttributes: Objects
)
