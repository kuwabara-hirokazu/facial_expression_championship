package com.example.facialexpressionchampionship.data.source.local

import com.example.facialexpressionchampionship.data.source.local.room.BattleHistory
import com.example.facialexpressionchampionship.data.source.local.room.BattleHistoryDao
import com.example.facialexpressionchampionship.data.source.local.room.BattleInformationEntity
import com.example.facialexpressionchampionship.data.source.local.room.ChallengerEntity
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

    fun deleteBattleHistory(battleInformation: BattleInformationEntity): Completable {
        return battleHistoryDao.deleteBattleHistory(battleInformation)
    }
}
