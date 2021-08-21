package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.facialexpressionchampionship.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BattleViewModel @Inject constructor() : ViewModel() {

    private val themeList = listOf(
        R.string.anger,
        R.string.contempt,
        R.string.disgust,
        R.string.fear,
        R.string.happiness,
        R.string.neutral,
        R.string.sadness,
        R.string.surprise
    )

    var battleTheme = ObservableField<Int>()

    fun setupBattleTheme() {
        battleTheme.set(themeList.shuffled()[0])
    }
}
