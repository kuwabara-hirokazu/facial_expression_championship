package com.example.facialexpressionchampionship.model

import com.example.facialexpressionchampionship.data.room.ChallengerEntity

data class Challenger(
    val name: String,
    val score: Float,
    val image: String,
    var ranking: String,
)

fun List<ChallengerEntity>.mapToChallenger(index: Int): Challenger? {
    if (index >= this.size) {
        return null
    }
    val challenger = this[index]
    return Challenger(challenger.name, challenger.score, challenger.image, challenger.ranking)
}
