package com.example.facialexpressionchampionship.viewmodel.battle

import com.example.facialexpressionchampionship.data.FaceDataRepository
import com.example.facialexpressionchampionship.data.RequestBodyCreator
import com.example.facialexpressionchampionship.model.FaceScore
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then

class ImageConfirmationViewModelTest {

    companion object {
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

        private val requestBody = object : RequestBody() {
            override fun contentType(): MediaType? = null
            override fun writeTo(sink: BufferedSink) = Unit
        }

        private const val DUMMY_URL = "/Users/kuwa/開発/Caraquri/FacialExpressionChampionship/app/src/test/resources/test_image.jpg"
    }

    @Mock
    private lateinit var repository: FaceDataRepository

    @Mock
    private lateinit var creator: RequestBodyCreator

    private lateinit var viewModel: ImageConfirmationViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel =
            ImageConfirmationViewModel(
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                repository,
                creator
            )
    }

    @Test
    fun testDetectFace() {
        // Given
        given(creator.create(any())).willReturn(requestBody)
        given(repository.detectFace(any())).willReturn(Single.just(FACE_SCORE))
        viewModel.imageUrl.set(DUMMY_URL)
        val testObserver = viewModel.score.test()

        // When
        viewModel.detectFace()

        // Then
        testObserver
            .awaitCount(1)
            .assertValue(FACE_SCORE)
            .assertNoErrors()

        then(repository).should().detectFace(requestBody)
    }
}
