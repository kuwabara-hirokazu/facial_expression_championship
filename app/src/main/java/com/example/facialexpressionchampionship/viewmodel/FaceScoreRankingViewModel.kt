package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.R
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class FaceScoreRankingViewModel @Inject constructor() : BaseViewModel() {

    var challengeName = ObservableField<String>()

    val inValid: PublishSubject<Int> = PublishSubject.create()

    fun saveRanking() {
        val name = challengeName.get()
        if (name.isNullOrEmpty()) {
            inValid.onNext(R.string.enter_challenge_name)
            return
        }

    }
}
