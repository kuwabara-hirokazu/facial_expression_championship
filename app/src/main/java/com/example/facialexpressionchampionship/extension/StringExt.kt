package com.example.facialexpressionchampionship.extension

fun String.hundredfold() : String {
    return (this.toFloat() * 100).toString()
}

fun String.hundredfoldToFloat() : Float {
    return (this.toFloat() * 100)
}
