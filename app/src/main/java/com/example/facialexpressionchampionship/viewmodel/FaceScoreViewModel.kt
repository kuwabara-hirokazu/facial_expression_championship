package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.model.FaceScore
import com.example.facialexpressionchampionship.model.ScoreCache
import com.example.facialexpressionchampionship.model.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class FaceScoreViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        private const val CHALLENGE_COUNT_MIN = 0
        private const val CHALLENGE_COUNT_MAX = 3
    }

    var imageUrl = ObservableField<String>()
    var challenger = ObservableField<String>()

    private lateinit var score: FaceScore
    var themeScore = ObservableField<Float>()
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

    fun setScore(score: FaceScore, themeType: ThemeType) {
        this.score = score
        themeScore.set(score.getThemeScoreFrom(themeType))
        anger.set(score.anger)
        contempt.set(score.contempt)
        disgust.set(score.disgust)
        fear.set(score.fear)
        happiness.set(score.happiness)
        neutral.set(score.neutral)
        sadness.set(score.sadness)
        surprise.set(score.surprise)
    }

    fun checkBattleCount(count: Int) {
        isNextChallengerClickEnabled.set(count != CHALLENGE_COUNT_MAX)
        isRankingClickEnabled.set(count != CHALLENGE_COUNT_MIN)
    }

    fun getScoreData(): ScoreCache? {
        val name = challenger.get()
        if (name.isNullOrEmpty()) {
            conditionInvalid.onNext(R.string.enter_name)
            return null
        }

        return themeScore.get()?.let { themeScore ->
            imageUrl.get()?.let { url ->
                ScoreCache(name, themeScore, score, url, null)
            }
        }
    }
}
