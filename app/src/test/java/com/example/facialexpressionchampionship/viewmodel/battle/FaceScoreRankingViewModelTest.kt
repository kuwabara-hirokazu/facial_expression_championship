package com.example.facialexpressionchampionship.viewmodel.battle

import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.data.repository.BattleHistoryRepository
import com.example.facialexpressionchampionship.data.source.local.SharedPreferencesData
import com.example.facialexpressionchampionship.model.FaceScore
import com.example.facialexpressionchampionship.model.ScoreData
import com.example.facialexpressionchampionship.model.ThemeType
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.given

class FaceScoreRankingViewModelTest {

    companion object {
        val BATTLE_THEME = ThemeType.HAPPINESS
        private val FACE_SCORE = FaceScore(
            anger = 0.0.toFloat(),
            contempt = 0.1.toFloat(),
            disgust = 0.0.toFloat(),
            fear = 0.0.toFloat(),
            happiness = 47.0.toFloat(),
            neutral = 52.7.toFloat(),
            sadness = 0.0.toFloat(),
            surprise = 0.0.toFloat()
        )
        private val SCORE1 = ScoreData("Taro", 47.0.toFloat(), FACE_SCORE, "imageUrl")
        private val SCORE2 = ScoreData("JIRO", 47.0.toFloat(), FACE_SCORE, "imageUrl")
        val SCORE_DATA: List<ScoreData> = listOf(SCORE1, SCORE2)
    }

    @Mock
    private lateinit var repository: BattleHistoryRepository

    @Mock
    lateinit var preferences: SharedPreferencesData

    private lateinit var viewModel: FaceScoreRankingViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = FaceScoreRankingViewModel(
            Schedulers.trampoline(),
            Schedulers.trampoline(),
            repository,
            preferences
        )
    }

    @Test
    fun testSaveRanking() {
        // Given
        given(repository.saveBattleInformation(any())).willReturn(Completable.complete())
        given(repository.saveChallenger(any())).willReturn(Completable.complete())
        viewModel.challengeName.set("testName")
        val testObserver = viewModel.savedHistory.test()

        // When
        viewModel.saveRanking(BATTLE_THEME, SCORE_DATA)

        // Then
        testObserver
            .assertValue(R.string.saved_challenge_result)
            .assertNoErrors()
    }
}
