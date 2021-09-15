package com.example.facialexpressionchampionship.viewmodel

import com.example.facialexpressionchampionship.data.BattleHistoryRepository
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class BattleHistoryViewModel @Inject constructor(
    private val repository: BattleHistoryRepository
) : BaseViewModel() {

    val historyList: BehaviorSubject<List<BattleHistoryBusinessModel>> = BehaviorSubject.create()

    fun loadHistory() {
        repository.getBattleHistory()
            .execute(
                onSuccess = {
                    historyList.onNext(it)
                },
                retry = { loadHistory() }
            )
    }
}
