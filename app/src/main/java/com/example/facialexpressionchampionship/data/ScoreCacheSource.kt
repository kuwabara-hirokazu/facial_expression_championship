package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.model.ScoreCache
import io.reactivex.rxjava3.core.Single

interface ScoreCacheSource {
    fun getScoreList(): Single<List<ScoreCache>>

    fun getScoreCount(): Int

    fun addScoreList(score: ScoreCache)

    fun clearCache()
}
