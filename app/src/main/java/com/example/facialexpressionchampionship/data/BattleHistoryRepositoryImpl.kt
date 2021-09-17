package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.data.room.BattleHistory
import com.example.facialexpressionchampionship.data.room.BattleInformationEntity
import com.example.facialexpressionchampionship.data.room.ChallengerEntity
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import com.example.facialexpressionchampionship.model.mapToBattleHistoryBusinessModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BattleHistoryRepositoryImpl @Inject constructor(
    private val localData: LocalData
) : BattleHistoryRepository {

    override fun saveBattleInformation(battleInformation: BattleInformationEntity): Completable {
        return localData.saveBattleInformation(battleInformation)
    }

    override fun saveChallenger(challenger: List<ChallengerEntity>): Completable {
        return localData.saveChallenger(challenger)
    }

    override fun getBattleHistory(): Single<List<BattleHistoryBusinessModel>> {
        return localData.getBattleHistory().flatMap {
            Single.just(it.mapToBattleHistoryBusinessModel())
        }
    }

    override fun deleteBattleHistory(battleInformation: BattleInformationEntity): Completable {
        return localData.deleteBattleHistory(battleInformation)
    }

}
