package com.example.facialexpressionchampionship.viewmodel.history

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.data.repository.BattleHistoryRepository
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import com.example.facialexpressionchampionship.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BattleHistoryViewModel @Inject constructor(
    @Named("observeOnScheduler") observeOnScheduler: Scheduler,
    @Named("subscribeOnScheduler") subscribeOnScheduler: Scheduler,
    private val repository: BattleHistoryRepository
) : BaseViewModel(observeOnScheduler, subscribeOnScheduler) {

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
