package com.example.facialexpressionchampionship.model

import com.example.facialexpressionchampionship.data.room.BattleHistory
import java.io.Serializable

data class BattleHistoryBusinessModel(
    val battleId: Int,
    val battleName: String,
    val battleTheme: ThemeType,
    val challenger1: Challenger?,
    val challenger2: Challenger?,
    val challenger3: Challenger?,
    val challenger4: Challenger?
): Serializable

fun List<BattleHistory>.mapToBattleHistoryBusinessModel(): List<BattleHistoryBusinessModel> {
    val list = mutableListOf<BattleHistoryBusinessModel>()

    this.forEach {
        val businessModel = BattleHistoryBusinessModel(
            it.battleInformation.battleId,
            it.battleInformation.battleName,
            it.battleInformation.battleTheme,
            it.challenger.mapToChallenger(0),
            it.challenger.mapToChallenger(1),
            it.challenger.mapToChallenger(2),
            it.challenger.mapToChallenger(3)
        )
        list.add(businessModel)
    }
    return list
}
