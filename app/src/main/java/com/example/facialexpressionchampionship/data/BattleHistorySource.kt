package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.data.room.BattleHistory
import com.example.facialexpressionchampionship.data.room.BattleInformationEntity
import com.example.facialexpressionchampionship.data.room.ChallengerEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface BattleHistorySource {

    fun saveBattleInformation(battleInformation: BattleInformationEntity): Completable

    fun saveChallenger(challenger: List<ChallengerEntity>): Completable

    fun getBattleHistory(): Single<List<BattleHistory>>
}
