package com.example.facialexpressionchampionship.viewmodel.battle

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.model.ScoreData
import com.example.facialexpressionchampionship.model.ThemeType
import com.example.facialexpressionchampionship.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BattleViewModel @Inject constructor() : BaseViewModel() {

    private val themeType = ThemeType.values().toList().shuffled().first()
    private val scoreDataList = mutableListOf<ScoreData>()
    var battleTheme = ObservableField<Int>()

    fun getTheme(): ThemeType {
        return themeType
    }

    fun setTheme() {
        battleTheme.set(themeType.theme)
    }

    fun getScoreList(): List<ScoreData> {
        return scoreDataList.sortedBy { it.ranking }
    }

    fun addScoreList(score: ScoreData) {
        scoreDataList.add(score)
    }

    fun changeRank() {
        scoreDataList
            .sortedByDescending { it.score.getThemeScoreFrom(getTheme()) }
            .forEachIndexed { index, scoreData ->
                scoreData.ranking = (index + 1).toString()
            }
    }
}
