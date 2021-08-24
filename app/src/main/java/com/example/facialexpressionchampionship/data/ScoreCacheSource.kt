package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.model.ScoreCache

interface ScoreCacheSource {
    fun getScoreList(): List<ScoreCache>

    fun getScoreCount(): Int

    fun addScoreList(score: ScoreCache)

    fun clearCache()
}
