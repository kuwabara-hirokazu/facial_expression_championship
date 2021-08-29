package com.example.facialexpressionchampionship.data.room

import androidx.room.*

@Dao
interface BattleHistoryDao {

    @Insert
    @JvmSuppressWildcards
    fun saveBattleHistory(battleInformation: BattleInformationEntity, scoreList: List<ScoreListEntity>)

    @Delete
    fun deleteBattleHistory(battleInformation: BattleInformationEntity)

    @Transaction
    @Query("SELECT * FROM battle_information")
    fun getBattleHistory(): List<BattleHistory>
}
