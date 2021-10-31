package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.data.BattleHistoryRepository
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class BattleHistoryViewModel @Inject constructor(
    private val repository: BattleHistoryRepository
) : BaseViewModel() {

    var hasHistory = ObservableField<Boolean>()

    val historyList: BehaviorSubject<List<BattleHistoryBusinessModel>> = BehaviorSubject.create()

    fun loadHistory() {
        repository.getBattleHistory()
            .execute(
                onSuccess = {
                    hasHistory.set(it.isNotEmpty())
                    historyList.onNext(it)
                },
                retry = { loadHistory() }
            )
    }
}
