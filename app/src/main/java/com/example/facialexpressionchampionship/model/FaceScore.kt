package com.example.facialexpressionchampionship.model

import java.io.Serializable

data class FaceScore(
    val theme: Float,
    val anger: String,
    val contempt: String,
    val disgust: String,
    val fear: String,
    val happiness: String,
    val neutral: String,
    val sadness: String,
    val surprise: String
) : Serializable
