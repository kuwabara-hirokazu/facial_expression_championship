package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.data.room.BattleInformationEntity
import com.example.facialexpressionchampionship.data.room.ChallengerEntity
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface BattleHistorySource {

    fun saveBattleInformation(battleInformation: BattleInformationEntity): Completable

    fun saveChallenger(challenger: List<ChallengerEntity>): Completable

    fun getBattleHistory(): Single<List<BattleHistoryBusinessModel>>
}
