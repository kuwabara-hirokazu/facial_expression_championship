package com.example.facialexpressionchampionship.viewmodel.history

import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.data.BattleHistoryRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.given

class BattleHistoryDetailViewModelTest : TestCase() {

    @Mock
    private lateinit var repository: BattleHistoryRepository

    private lateinit var viewModel: BattleHistoryDetailViewModel

    @Before
    override fun setUp() {
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
        val testObserver = viewModel.deleted.test()

        // When
        viewModel.deleteHistory()

        // Then
        testObserver
            .assertValue(R.string.deleted_challenge_history)
            .assertNoErrors()

    }
}
