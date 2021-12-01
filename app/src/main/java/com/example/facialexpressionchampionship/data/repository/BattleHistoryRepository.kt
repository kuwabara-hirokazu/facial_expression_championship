package com.example.facialexpressionchampionship.data.repository

import com.example.facialexpressionchampionship.data.source.local.room.BattleInformationEntity
import com.example.facialexpressionchampionship.data.source.local.room.ChallengerEntity
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface BattleHistoryRepository {

    fun saveBattleInformation(battleInformation: BattleInformationEntity): Completable

    fun saveChallenger(challenger: List<ChallengerEntity>): Completable

    fun getBattleHistory(): Single<List<BattleHistoryBusinessModel>>

    fun deleteBattleHistory(battleInformation: BattleInformationEntity): Completable
}
