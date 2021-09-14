package com.example.facialexpressionchampionship.model

data class ScoreCache(
    val name: String,
    val themeScore: Float,
    val score: FaceScore,
    val image: String,
    var ranking: String?
)
