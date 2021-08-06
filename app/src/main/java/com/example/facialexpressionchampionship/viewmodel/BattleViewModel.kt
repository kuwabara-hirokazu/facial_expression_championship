package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BattleViewModel @Inject constructor() : ViewModel() {

    var battleTheme = ObservableField<String>()
}
