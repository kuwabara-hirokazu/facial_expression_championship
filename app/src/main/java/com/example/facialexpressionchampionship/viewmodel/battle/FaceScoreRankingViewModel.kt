package com.example.facialexpressionchampionship.viewmodel.battle

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.data.repository.BattleHistoryRepository
import com.example.facialexpressionchampionship.data.source.local.SharedPreferencesData
import com.example.facialexpressionchampionship.data.source.local.room.BattleInformationEntity
import com.example.facialexpressionchampionship.data.source.local.room.ChallengerEntity
import com.example.facialexpressionchampionship.model.ScoreData
import com.example.facialexpressionchampionship.model.ThemeType
import com.example.facialexpressionchampionship.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FaceScoreRankingViewModel @Inject constructor(
    @Named("observeOnScheduler") observeOnScheduler: Scheduler,
    @Named("subscribeOnScheduler") subscribeOnScheduler: Scheduler,
    private val repository: BattleHistoryRepository,
    private val sharedPreference: SharedPreferencesData
) : BaseViewModel(observeOnScheduler, subscribeOnScheduler) {

    var challengeName = ObservableField<String>()

    val inValid: PublishSubject<Int> = PublishSubject.create()

    val savedHistory: PublishSubject<Int> = PublishSubject.create()

    fun saveRanking(battleTheme: ThemeType, scoreDataList: List<ScoreData>) {
        val name = challengeName.get()
        if (name.isNullOrEmpty()) {
            inValid.onNext(R.string.enter_challenge_name)
            return
        }

        val battleInformation = BattleInformationEntity(sharedPreference.getBattleId(), name, battleTheme)
        repository.saveBattleInformation(battleInformation)
            .andThen(repository.saveChallenger(createChallengerList(scoreDataList)))
            .execute(
                onComplete = {
                    sharedPreference.saveBattleId(sharedPreference.getBattleId())
                    savedHistory.onNext(R.string.saved_challenge_result)
                },
                retry = { saveRanking(battleTheme, scoreDataList) }
            )
    }

    private fun createChallengerList(scoreDataList: List<ScoreData>): List<ChallengerEntity> {
        return scoreDataList.mapIndexed { index, scoreCache ->
            ChallengerEntity(
                0,
                sharedPreference.getBattleId(),
                scoreCache.name,
                scoreCache.themeScore,
                scoreCache.image,
                index + 1
            )
        }
    }
}
