package com.example.facialexpressionchampionship.extension

fun Float.floor(): Float {
    return (kotlin.math.floor(this * 10.0) / 10.0).toFloat()
}
