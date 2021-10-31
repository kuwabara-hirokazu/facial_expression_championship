package com.example.facialexpressionchampionship.model

import com.example.facialexpressionchampionship.data.room.BattleHistory

data class BattleHistoryBusinessModel(
    val battleId: Int,
    val battleName: String,
    val battleTheme: Int,
    val challenger1: Challenger?,
    val challenger2: Challenger?,
    val challenger3: Challenger?,
    val challenger4: Challenger?
)

fun List<BattleHistory>.mapToBattleHistoryBusinessModel(): List<BattleHistoryBusinessModel> {
    return this.map {
        BattleHistoryBusinessModel(
            it.battleInformation.battleId,
            it.battleInformation.battleName,
            it.battleInformation.battleTheme.theme,
            it.challenger.getOrNull(0)?.toChallenger(),
            it.challenger.getOrNull(1)?.toChallenger(),
            it.challenger.getOrNull(2)?.toChallenger(),
            it.challenger.getOrNull(3)?.toChallenger()
        )
    }
}