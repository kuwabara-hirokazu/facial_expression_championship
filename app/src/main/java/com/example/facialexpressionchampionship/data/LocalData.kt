package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.data.room.BattleHistory
import com.example.facialexpressionchampionship.data.room.BattleHistoryDao
import com.example.facialexpressionchampionship.data.room.BattleInformationEntity
import com.example.facialexpressionchampionship.data.room.ChallengerEntity
import com.example.facialexpressionchampionship.model.Emotion
import com.example.facialexpressionchampionship.model.ScoreCache
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalData @Inject constructor(private val battleHistoryDao: BattleHistoryDao) {

    companion object {
        var themeType = ThemeType.values().toList().shuffled().first()
        val scoreCacheList = mutableListOf<ScoreCache>()
    }

    private fun getThemeId(): Int {
        return themeType.id
    }

    fun getTheme(): Int {
        return themeType.theme
    }

    fun changeTheme() {
        themeType = ThemeType.values().toList().shuffled().first()
    }

    fun getThemeScore(emotion: Emotion): String {
        val emotionList = listOf(
            emotion.anger,
            emotion.contempt,
            emotion.disgust,
            emotion.fear,
            emotion.happiness,
            emotion.neutral,
            emotion.sadness,
            emotion.surprise
        )
        return emotionList[getThemeId()]
    }

    fun getScoreList(): List<ScoreCache> {
        return scoreCacheList.sortedBy { it.ranking }
    }

    fun addScoreList(score: ScoreCache): Completable {
        return Completable.fromAction {
            scoreCacheList.add(score)
            changeRank()
        }
    }

    fun clearCache(): Completable  {
        return Completable.fromAction {
            scoreCacheList.clear()
        }
    }

    private fun changeRank() {
        scoreCacheList
            .sortedByDescending { it.score.theme }
            .forEachIndexed { index, scoreCache ->
                scoreCache.ranking = (index + 1).toString()
            }
    }

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

enum class ThemeType(val id: Int, val theme: Int) {
    ANGER(0, R.string.anger),
    CONTEMPT(1, R.string.contempt),
    DISGUST(2, R.string.disgust),
    FEAR(3, R.string.fear),
    HAPPINESS(4, R.string.happiness),
    NEUTRAL(5, R.string.neutral),
    SADNESS(6, R.string.sadness),
    SURPRISE(7, R.string.surprise)
}
