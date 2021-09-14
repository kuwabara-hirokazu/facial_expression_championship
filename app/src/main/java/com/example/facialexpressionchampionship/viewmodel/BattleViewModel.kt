package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.model.ScoreCache
import com.example.facialexpressionchampionship.model.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BattleViewModel @Inject constructor() : BaseViewModel() {

    private val themeType = ThemeType.values().toList().shuffled().first()
    private val scoreCacheList = mutableListOf<ScoreCache>()
    var battleTheme = ObservableField<Int>()

    fun getTheme(): ThemeType {
        return themeType
    }

    fun setTheme() {
        battleTheme.set(themeType.theme)
    }

    fun getScoreList(): List<ScoreCache> {
        return scoreCacheList
    }

    fun addScoreList(score: ScoreCache) {
        scoreCacheList.add(score)
    }

    fun changeRank() {
        scoreCacheList
            .sortedByDescending { it.score.getThemeScoreFrom(getTheme()) }
            .forEachIndexed { index, scoreCache ->
                scoreCache.ranking = (index + 1).toString()
            }
    }
}
