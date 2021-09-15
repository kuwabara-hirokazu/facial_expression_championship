package com.example.facialexpressionchampionship.extension

fun String.hundredfoldToFloat() : Float {
    return (this.toFloat() * 100).floor()
}
