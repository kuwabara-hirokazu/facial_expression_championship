package com.example.facialexpressionchampionship.extension

fun String.hundredfold() : String {
    return (this.toFloat() * 100).floor().toString()
}

fun String.hundredfoldToFloat() : Float {
    return (this.toFloat() * 100).floor()
}
