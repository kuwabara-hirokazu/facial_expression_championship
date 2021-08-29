package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.model.ScoreCache
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ScoreCacheRepository @Inject constructor(
    private val localData: LocalData
) : ScoreCacheSource {

    override fun getScoreList(): Single<List<ScoreCache>> {
        return Single.just(localData.getScoreList())
    }

    override fun getScoreCount(): Single<Int> {
        return Single.just(localData.getScoreList().size)
    }

    override fun addScoreList(score: ScoreCache): Completable {
        return localData.addScoreList(score)
    }

    override fun clearCache() {
        localData.clearCache()
    }
}
