package com.example.facialexpressionchampionship.data.room

import androidx.room.Embedded
import androidx.room.Relation

data class BattleHistory(
    @Embedded val battleInformation: BattleInformationEntity,
    @Relation(
        parentColumn = "battle_id",
        entityColumn = "battle_creator_id"
    )
    val scoreList: List<ScoreListEntity>
)
