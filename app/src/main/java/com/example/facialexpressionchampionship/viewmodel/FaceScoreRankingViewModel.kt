package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.data.ScoreCacheRepository
import com.example.facialexpressionchampionship.model.ScoreCache
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class FaceScoreRankingViewModel @Inject constructor(
    private val cacheRepository: ScoreCacheRepository
) : BaseViewModel() {

    var challengeName = ObservableField<String>()

    val rankingList: BehaviorSubject<List<ScoreCache>> = BehaviorSubject.create()

    val inValid: PublishSubject<Int> = PublishSubject.create()

    fun loadScoreRanking() {
        cacheRepository.getScoreList()
            .execute(
                onSuccess = { rankingList.onNext(it) },
                retry = { loadScoreRanking() }
            )
    }

    fun saveRanking() {
        val name = challengeName.get()
        if (name.isNullOrEmpty()) {
            inValid.onNext(R.string.enter_challenge_name)
            return
        }

    }
}
