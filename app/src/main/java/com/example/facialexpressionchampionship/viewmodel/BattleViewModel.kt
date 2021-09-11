package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.data.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BattleViewModel @Inject constructor(
) : BaseViewModel() {

    var themeType = ThemeType.values().toList().shuffled().first()
    var battleTheme = ObservableField<Int>()

    fun setTheme() {
        battleTheme.set(themeType.theme)
    }
}
