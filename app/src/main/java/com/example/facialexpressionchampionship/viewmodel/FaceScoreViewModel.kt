package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.data.ScoreCacheSource
import com.example.facialexpressionchampionship.model.FaceScore
import com.example.facialexpressionchampionship.model.ScoreCache
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class FaceScoreViewModel @Inject constructor(
    private val cacheRepository: ScoreCacheSource
) : BaseViewModel() {

    companion object {
        private const val CHALLENGE_COUNT_MIN = 0
        private const val CHALLENGE_COUNT_MAX = 3
    }

    var imageUrl = ObservableField<String>()

    var challenger = ObservableField<String>()

    private lateinit var score: FaceScore

    var themeScore = ObservableField<String>()

    var anger = ObservableField<String>()

    var contempt = ObservableField<String>()

    var disgust = ObservableField<String>()

    var fear = ObservableField<String>()

    var happiness = ObservableField<String>()

    var neutral = ObservableField<String>()

    var sadness = ObservableField<String>()

    var surprise = ObservableField<String>()

    var isNextChallengerClickEnabled = ObservableField<Boolean>()

    var isRankingClickEnabled = ObservableField<Boolean>()

    val conditionInvalid: PublishSubject<Int> = PublishSubject.create()

    fun setScore(score: FaceScore) {
        this.score = score
        themeScore.set(score.theme)
        anger.set(score.anger)
        contempt.set(score.contempt)
        disgust.set(score.disgust)
        fear.set(score.fear)
        happiness.set(score.happiness)
        neutral.set(score.neutral)
        sadness.set(score.sadness)
        surprise.set(score.surprise)
    }

    fun setup() {
        val scoreCount = cacheRepository.getScoreCount()
        isNextChallengerClickEnabled.set(scoreCount != CHALLENGE_COUNT_MAX)
        isRankingClickEnabled.set(scoreCount != CHALLENGE_COUNT_MIN)
    }

    fun hasSavedScore(): Boolean {
        val name = challenger.get()
        if (name.isNullOrEmpty()) {
            conditionInvalid.onNext(R.string.enter_name)
            return false
        }

        val score = imageUrl.get()?.let { ScoreCache(name, score, it, null) } ?: return false
        cacheRepository.addScoreList(score)
        return true
    }
}
