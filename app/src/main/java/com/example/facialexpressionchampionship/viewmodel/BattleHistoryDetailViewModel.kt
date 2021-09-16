package com.example.facialexpressionchampionship.viewmodel

import com.example.facialexpressionchampionship.data.BattleHistoryRepository
import javax.inject.Inject

class BattleHistoryDetailViewModel @Inject constructor(
    private val repository: BattleHistoryRepository
): BaseViewModel() {

}
