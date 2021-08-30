package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.data.room.BattleHistory
import com.example.facialexpressionchampionship.data.room.BattleInformationEntity
import com.example.facialexpressionchampionship.data.room.ChallengerEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BattleHistoryRepository @Inject constructor(
    private val localData: LocalData
) : BattleHistorySource {

    override fun saveBattleInformation(battleInformation: BattleInformationEntity): Completable {
        return localData.saveBattleInformation(battleInformation)
    }

    override fun saveChallenger(challenger: List<ChallengerEntity>): Completable {
        return localData.saveChallenger(challenger)
    }

    override fun getBattleHistory(): Single<List<BattleHistory>> {
        return localData.getBattleHistory()
    }

}
