package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.model.FaceScore
import com.example.facialexpressionchampionship.model.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BattleViewModel @Inject constructor(
) : BaseViewModel() {

    private val themeType = ThemeType.values().toList().shuffled().first()
    var battleTheme = ObservableField<Int>()

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

    fun setTheme() {
        battleTheme.set(themeType.theme)
    }

    fun setScore(score: FaceScore) {
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
}
