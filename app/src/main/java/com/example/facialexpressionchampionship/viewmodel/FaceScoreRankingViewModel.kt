package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.data.ScoreCacheRepository
import com.example.facialexpressionchampionship.model.ScoreCache
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class FaceScoreRankingViewModel @Inject constructor(
    private val cacheRepository: ScoreCacheRepository
) : BaseViewModel() {
    
    var challengeName = ObservableField<String>()

    val rankingList: BehaviorSubject<List<ScoreCache>> = BehaviorSubject.create()

    fun loadScoreRanking() {
        cacheRepository.getScoreList()
            .execute(
                onSuccess = { rankingList.onNext(it) },
                retry = { loadScoreRanking() }
            )
    }
}
