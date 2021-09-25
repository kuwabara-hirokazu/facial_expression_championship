package com.example.facialexpressionchampionship.viewmodel.battle

import com.example.facialexpressionchampionship.data.FaceDataRepository
import com.example.facialexpressionchampionship.extension.toByteArray
import com.example.facialexpressionchampionship.model.FaceScore
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.TestCase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.io.File

class ImageConfirmationViewModelTest : TestCase() {

    companion object {
        private const val MEDEA_TYPE = "application/octet-stream"
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

        private val FACE_SCORE2 = FaceScore(
            anger = 100.0.toFloat(),
            contempt = 0.0.toFloat(),
            disgust = 0.0.toFloat(),
            fear = 0.0.toFloat(),
            happiness = 0.0.toFloat(),
            neutral = 0.0.toFloat(),
            sadness = 0.0.toFloat(),
            surprise = 0.0.toFloat()
        )
    }

    @Mock
    private lateinit var repository: FaceDataRepository

    private lateinit var viewModel: ImageConfirmationViewModel

    @Before
    override fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel =
            ImageConfirmationViewModel(Schedulers.trampoline(), Schedulers.trampoline(), repository)
    }

    @Test
    fun testDetectFace() {
        // Given
        val byte = File("https://ranking.xgoo.jp/image_proxy/resize/w_282_h_282/tool/images/talent/2000072988.jpg?pos=4").toByteArray() ?: return
        val binaryData = byte.toRequestBody(MEDEA_TYPE.toMediaTypeOrNull(), 0, byte.size)
        given(repository.detectFace(binaryData)).willReturn(Single.just(FACE_SCORE))
        val testObserver = viewModel.score.test()

        // When
        viewModel.detectFace()

        // Then
        testObserver
            .assertValue(FACE_SCORE)
            .assertValue(FACE_SCORE2)
            .assertNoErrors()

        then(repository).should().detectFace(binaryData)
    }
}
