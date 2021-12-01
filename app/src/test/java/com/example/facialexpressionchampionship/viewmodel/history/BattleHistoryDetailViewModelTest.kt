package com.example.facialexpressionchampionship.viewmodel.history

import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.data.repository.BattleHistoryRepository
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import com.example.facialexpressionchampionship.model.Challenger
import com.example.facialexpressionchampionship.model.ThemeType
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.given

class BattleHistoryDetailViewModelTest {

    companion object {
        private val CHALLENGER1 = Challenger("Taro", 47.0.toFloat(), "imageUrl", 1)
        private val CHALLENGER2 = Challenger("Taro", 37.0.toFloat(), "imageUrl", 2)
        private val TEST_DATA = BattleHistoryBusinessModel(
            1,
            "testBattle",
            ThemeType.HAPPINESS,
            CHALLENGER1,
            CHALLENGER2,
            null,
            null
        )
    }

    @Mock
    private lateinit var repository: BattleHistoryRepository

    private lateinit var viewModel: BattleHistoryDetailViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = BattleHistoryDetailViewModel(
            Schedulers.trampoline(),
            Schedulers.trampoline(),
            repository
        )
    }

    @Test
    fun testDeleteHistory() {
        // Given
        given(repository.deleteBattleHistory(any())).willReturn(Completable.complete())
        viewModel.history.set(TEST_DATA)
        val testObserver = viewModel.deleted.test()

        // When
        viewModel.deleteHistory()

        // Then
        testObserver
            .assertValue(R.string.deleted_challenge_history)
            .assertNoErrors()
    }
}
