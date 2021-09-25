package com.example.facialexpressionchampionship.viewmodel.battle

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.model.ScoreData
import com.example.facialexpressionchampionship.model.ThemeType
import com.example.facialexpressionchampionship.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BattleViewModel @Inject constructor(
    @Named("observeOnScheduler") observeOnScheduler: Scheduler,
    @Named("subscribeOnScheduler") subscribeOnScheduler: Scheduler,
) : BaseViewModel(observeOnScheduler, subscribeOnScheduler) {

    private val scoreDataList = mutableListOf<ScoreData>()
    val themeType = ThemeType.values().toList().shuffled().first()
    var battleTheme = ObservableField<Int>()

    fun setTheme() {
        battleTheme.set(themeType.theme)
    }

    fun getSortedScoreList(): List<ScoreData> {
        return scoreDataList.sortedByDescending { it.score.getThemeScoreFrom(themeType) }
    }

    fun addScoreList(score: ScoreData) {
        scoreDataList.add(score)
    }
}
