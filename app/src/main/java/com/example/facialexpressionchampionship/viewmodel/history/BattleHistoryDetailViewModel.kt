package com.example.facialexpressionchampionship.viewmodel.history

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.data.BattleHistoryRepository
import com.example.facialexpressionchampionship.data.room.BattleInformationEntity
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import com.example.facialexpressionchampionship.model.Challenger
import com.example.facialexpressionchampionship.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BattleHistoryDetailViewModel @Inject constructor(
    @Named("observeOnScheduler") observeOnScheduler: Scheduler,
    @Named("subscribeOnScheduler") subscribeOnScheduler: Scheduler,
    private val repository: BattleHistoryRepository
) : BaseViewModel(observeOnScheduler, subscribeOnScheduler) {

    var history = ObservableField<BattleHistoryBusinessModel>()

    val deleted: PublishSubject<Int> = PublishSubject.create()

    fun getChallengerList(): List<Challenger> {
        val challengerList = mutableListOf<Challenger>()
        val history = history.get() ?: return challengerList
        history.challenger1?.let { challengerList.add(it) }
        history.challenger2?.let { challengerList.add(it) }
        history.challenger3?.let { challengerList.add(it) }
        history.challenger4?.let { challengerList.add(it) }
        return challengerList
    }

    fun deleteHistory() {
        val history = history.get() ?: return
        val information = BattleInformationEntity(history.battleId, history.battleName, history.battleTheme)
        repository.deleteBattleHistory(information)
            .execute(
                onComplete = { deleted.onNext(R.string.deleted_challenge_history) },
                retry = { deleteHistory() }
            )
    }
}
