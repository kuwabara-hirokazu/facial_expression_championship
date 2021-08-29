package com.example.facialexpressionchampionship.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "score_list",
    foreignKeys = [ForeignKey(
        entity = BattleInformationEntity::class,
        parentColumns = arrayOf("battle_id"),
        childColumns = arrayOf("battle_creator_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ScoreListEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "score_list_id")
    val scoreListId: Int,
    @ColumnInfo(name = "battle_creator_id")
    val battleCreatorId: Int,
    val name: String,
    val score: Float,
    val image: String,
    var ranking: String
)

