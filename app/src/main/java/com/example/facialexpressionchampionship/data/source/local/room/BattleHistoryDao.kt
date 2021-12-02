package com.example.facialexpressionchampionship.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
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
