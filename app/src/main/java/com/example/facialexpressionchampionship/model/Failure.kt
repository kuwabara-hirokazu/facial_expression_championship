package com.example.facialexpressionchampionship.model

data class Failure(
    val error: Throwable,
    val message: Int,
    val retry: () -> Unit
)
