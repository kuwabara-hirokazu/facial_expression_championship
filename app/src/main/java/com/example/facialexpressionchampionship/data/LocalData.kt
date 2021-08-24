package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.model.ScoreCache
import javax.inject.Inject

class LocalData @Inject constructor() {

    companion object {
        val scoreCacheList = mutableListOf<ScoreCache>()
    }

    val themeList = listOf(
        R.string.anger,
        R.string.contempt,
        R.string.disgust,
        R.string.fear,
        R.string.happiness,
        R.string.neutral,
        R.string.sadness,
        R.string.surprise
    )

    fun getScoreList(): List<ScoreCache> {
        return scoreCacheList
    }

    fun addScoreList(score: ScoreCache) {
        scoreCacheList.add(score)
    }

    fun clearCache() {
        scoreCacheList.clear()
    }
}
