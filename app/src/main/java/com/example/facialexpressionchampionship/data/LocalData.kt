package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.data.room.BattleHistory
import com.example.facialexpressionchampionship.data.room.BattleHistoryDao
import com.example.facialexpressionchampionship.data.room.BattleInformationEntity
import com.example.facialexpressionchampionship.data.room.ChallengerEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalData @Inject constructor(private val battleHistoryDao: BattleHistoryDao) {

    fun saveBattleInformation(battleInformation: BattleInformationEntity): Completable {
        return battleHistoryDao.saveBattleInformation(battleInformation)
    }

    fun saveChallenger(challenger: List<ChallengerEntity>): Completable {
        return battleHistoryDao.saveChallenger(challenger)
    }

    fun getBattleHistory(): Single<List<BattleHistory>> {
        return battleHistoryDao.getBattleHistory()
    }

}
