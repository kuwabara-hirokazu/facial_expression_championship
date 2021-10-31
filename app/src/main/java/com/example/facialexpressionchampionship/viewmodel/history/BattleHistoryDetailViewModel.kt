package com.example.facialexpressionchampionship.viewmodel.history

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.data.BattleHistoryRepository
import com.example.facialexpressionchampionship.data.room.BattleInformationEntity
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import com.example.facialexpressionchampionship.model.Challenger
import com.example.facialexpressionchampionship.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class BattleHistoryDetailViewModel @Inject constructor(
    private val repository: BattleHistoryRepository
) : BaseViewModel() {

    var history = ObservableField<BattleHistoryBusinessModel>()

    val deleted: PublishSubject<Int> = PublishSubject.create()

    fun getChallengerList(): List<Challenger> {
        return history.get()?.let { history ->
            listOf(
                history.challenger1,
                history.challenger2,
                history.challenger3,
                history.challenger4
            ).mapNotNull { it }
        } ?: emptyList()
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
