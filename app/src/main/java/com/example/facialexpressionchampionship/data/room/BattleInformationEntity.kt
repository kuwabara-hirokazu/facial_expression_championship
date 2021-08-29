package com.example.facialexpressionchampionship.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "battle_information")
data class BattleInformationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "battle_id")
    val battleId: Int,
    @ColumnInfo(name = "battle_name")
    val battleName: String,
    @ColumnInfo(name = "battle_theme")
    val battleTheme: Int
)
