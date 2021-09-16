package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.data.BattleHistoryRepository
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import com.example.facialexpressionchampionship.model.Challenger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BattleHistoryDetailViewModel @Inject constructor(
    private val repository: BattleHistoryRepository
) : BaseViewModel() {

    var history = ObservableField<BattleHistoryBusinessModel>()

    fun getChallengerList(): List<Challenger> {
        val challengerList = mutableListOf<Challenger>()
        val history = history.get() ?: return challengerList
        history.challenger1?.let { challengerList.add(it) }
        history.challenger2?.let { challengerList.add(it) }
        history.challenger3?.let { challengerList.add(it) }
        history.challenger4?.let { challengerList.add(it) }
        return challengerList
    }
}
