package com.example.facialexpressionchampionship.viewmodel.history

import com.example.facialexpressionchampionship.data.BattleHistoryRepository
import com.example.facialexpressionchampionship.model.BattleHistoryBusinessModel
import com.example.facialexpressionchampionship.model.Challenger
import com.example.facialexpressionchampionship.model.ThemeType
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import org.mockito.kotlin.then

class BattleHistoryViewModelTest : TestCase() {

    companion object {
        private val CHALLENGER1 = Challenger("Taro", 47.0.toFloat(), "imageUrl", 1)
        private val CHALLENGER2 = Challenger("Taro", 37.0.toFloat(), "imageUrl", 2)
        private val HISTORY1 = BattleHistoryBusinessModel(
            1,
            "testBattle",
            ThemeType.HAPPINESS,
            CHALLENGER1,
            CHALLENGER2,
            null,
            null
        )
        private val HISTORY2 = BattleHistoryBusinessModel(
            2,
            "testBattle2",
            ThemeType.HAPPINESS,
            CHALLENGER1,
            CHALLENGER2,
            null,
            null
        )
        private val HISTORY_LIST: List<BattleHistoryBusinessModel> = listOf(HISTORY1, HISTORY2)
    }

    @Mock
    private lateinit var repository: BattleHistoryRepository

    private lateinit var viewModel: BattleHistoryViewModel

    @Before
    override fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = BattleHistoryViewModel(
            Schedulers.trampoline(),
            Schedulers.trampoline(),
            repository
        )
    }

    @Test
    fun testLoadHistory() {
        // Given
        given(repository.getBattleHistory()).willReturn(Single.just(HISTORY_LIST))
        val testObserver = viewModel.historyList.test()

        // When
        viewModel.loadHistory()

        // Then
        testObserver
            .assertValue(HISTORY_LIST)
            .assertNoErrors()
            .let {
                assertEquals(HISTORY_LIST.size, it.values()[0].size)
                assertEquals(HISTORY1, it.values()[0][0])
                assertEquals(HISTORY2, it.values()[0][1])
            }

        then(repository).should().getBattleHistory()
    }
}
