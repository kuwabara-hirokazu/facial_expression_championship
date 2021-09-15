package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.SharedPreferencesWrapper
import com.example.facialexpressionchampionship.Signal
import com.example.facialexpressionchampionship.data.BattleHistoryRepository
import com.example.facialexpressionchampionship.data.room.BattleInformationEntity
import com.example.facialexpressionchampionship.data.room.ChallengerEntity
import com.example.facialexpressionchampionship.model.ScoreData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class FaceScoreRankingViewModel @Inject constructor(
    private val battleHistoryRepository: BattleHistoryRepository,
    private val sharedPreference: SharedPreferencesWrapper
) : BaseViewModel() {

    var challengeName = ObservableField<String>()

    val inValid: PublishSubject<Int> = PublishSubject.create()

    val savedHistory: BehaviorSubject<Signal> = BehaviorSubject.create()

    var battleTheme: Int = 0

    var scoreDataList = listOf<ScoreData>()

    fun saveRanking() {
        val name = challengeName.get()
        if (name.isNullOrEmpty()) {
            inValid.onNext(R.string.enter_challenge_name)
            return
        }

        val battleInformation = BattleInformationEntity(sharedPreference.getBattleId(), name, battleTheme)
        battleHistoryRepository.saveBattleInformation(battleInformation)
            .andThen(battleHistoryRepository.saveChallenger(createChallengerList()))
            .execute(
                onComplete = {
                    sharedPreference.saveBattleId(sharedPreference.getBattleId())
                    savedHistory.onNext(Signal)
                },
                retry = { saveRanking() }
            )
    }

    private fun createChallengerList(): List<ChallengerEntity> {
        val challengerList = mutableListOf<ChallengerEntity>()
        scoreDataList.forEach { scoreCache ->
            val ranking = scoreCache.ranking ?: return challengerList
            val challenger = ChallengerEntity(
                0,
                sharedPreference.getBattleId(),
                scoreCache.name,
                scoreCache.themeScore,
                scoreCache.image,
                ranking
            )
            challengerList.add(challenger)
        }
        return challengerList
    }
}
