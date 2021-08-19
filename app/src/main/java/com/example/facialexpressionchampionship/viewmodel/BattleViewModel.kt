package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BattleViewModel @Inject constructor() : ViewModel() {

    private val themeList = listOf("怒り", "軽蔑", "嫌悪", "恐れ", "幸せ", "通常", "悲しみ", "驚き")

    var battleTheme = ObservableField<String>()

    fun setupBattleTheme() {
        battleTheme.set(themeList.shuffled()[0])
    }
}
