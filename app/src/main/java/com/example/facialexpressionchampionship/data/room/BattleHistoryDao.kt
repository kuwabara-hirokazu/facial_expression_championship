package com.example.facialexpressionchampionship.data.room

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface BattleHistoryDao {

    @Insert
    fun saveBattleInformation(battleInformation: BattleInformationEntity): Completable

    @Insert
    fun saveChallenger(challenger: List<ChallengerEntity>): Completable

    @Delete
    fun deleteBattleHistory(battleInformation: BattleInformationEntity): Completable

    @Transaction
    @Query("SELECT * FROM battle_information")
    fun getBattleHistory(): Single<List<BattleHistory>>
}
