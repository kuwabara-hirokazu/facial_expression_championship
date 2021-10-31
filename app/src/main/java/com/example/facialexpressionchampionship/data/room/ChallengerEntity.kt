package com.example.facialexpressionchampionship.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "challenger",
    foreignKeys = [ForeignKey(
        entity = BattleInformationEntity::class,
        parentColumns = arrayOf("battle_id"),
        childColumns = arrayOf("battle_creator_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ChallengerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "challenger_id")
    val challengerId: Int = 0,
    @ColumnInfo(name = "battle_creator_id", index = true)
    val battleCreatorId: Int,
    val name: String,
    val score: Float,
    val image: String,
    val ranking: Int
)

