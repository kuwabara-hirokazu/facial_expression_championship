package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.Signal
import com.example.facialexpressionchampionship.data.ThemeSource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class BattleViewModel @Inject constructor(
    private val repository: ThemeSource
) : BaseViewModel() {

    var battleTheme = ObservableField<Int>()
    val decidedTheme: BehaviorSubject<Signal> = BehaviorSubject.create()

    fun setup() {
        repository.getTheme()
            .execute(
                onSuccess = {
                    battleTheme.set(it)
                    decidedTheme.onNext(Signal)
                },
                retry = { setup() }
            )
    }
}
