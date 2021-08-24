package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.model.ScoreCache
import javax.inject.Inject

class ScoreCacheRepository @Inject constructor(
    private val localData: LocalData
) : ScoreCacheSource {

    override fun getScoreList(): List<ScoreCache> {
        return localData.getScoreList()
    }

    override fun getScoreCount(): Int {
        return localData.getScoreList().size
    }

    override fun addScoreList(score: ScoreCache) {
        localData.addScoreList(score)
    }

    override fun clearCache() {
        localData.clearCache()
    }
}
