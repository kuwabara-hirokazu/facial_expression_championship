package com.example.facialexpressionchampionship.viewmodel.battle

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.data.FaceDataRepository
import com.example.facialexpressionchampionship.extension.toByteArray
import com.example.facialexpressionchampionship.model.FaceScore
import com.example.facialexpressionchampionship.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.subjects.BehaviorSubject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Named

interface RequestBodyCreator {
    fun create(url: String): RequestBody?
}

class RequestBodyCreatorImpl: RequestBodyCreator {
    companion object {
        private val MEDEA_TYPE = "application/octet-stream"
    }
    override fun create(url: String): RequestBody? {
        val byte = File(url)
            .toByteArray() ?: return null

        return byte.toRequestBody(MEDEA_TYPE.toMediaTypeOrNull(), 0, byte.size)
    }
}

val dummyUrl = "/Users/kuwa/開発/Caraquri/FacialExpressionChampionship/app/src/test/resources/test_image.jpg"

@HiltViewModel
class ImageConfirmationViewModel @Inject constructor(
    @Named("observeOnScheduler") observeOnScheduler: Scheduler,
    @Named("subscribeOnScheduler") subscribeOnScheduler: Scheduler,
    private val repository: FaceDataRepository,
    private val creator: RequestBodyCreator
) : BaseViewModel(observeOnScheduler, subscribeOnScheduler) {

    private val MEDEA_TYPE = "application/octet-stream"

    var imageUrl = ObservableField<String>()
    var isShowProgress = ObservableField<Boolean>()
    val score: BehaviorSubject<FaceScore> = BehaviorSubject.create()

    fun detectFace() {
        val binaryData = creator.create(dummyUrl) ?: return

        isShowProgress.set(true)
        repository.detectFace(binaryData)
            .execute(
                onSuccess = {
                    Timber.d(it.toString())
                    isShowProgress.set(false)
                    score.onNext(it)
                },
                retry = { detectFace() }
            )
    }
}
