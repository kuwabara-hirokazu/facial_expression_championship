package com.example.facialexpressionchampionship.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BattleInformationEntity::class, ChallengerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BattleHistoryDatabase : RoomDatabase() {
    abstract val battleHistoryDao: BattleHistoryDao
}
