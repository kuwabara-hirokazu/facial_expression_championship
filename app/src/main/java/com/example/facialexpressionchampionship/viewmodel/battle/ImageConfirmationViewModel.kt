package com.example.facialexpressionchampionship.viewmodel.battle

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.data.FaceDataRepository
import com.example.facialexpressionchampionship.data.RequestBodyCreator
import com.example.facialexpressionchampionship.model.FaceScore
import com.example.facialexpressionchampionship.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

val dummyUrl = "/Users/kuwa/開発/Caraquri/FacialExpressionChampionship/app/src/test/resources/test_image.jpg"

@HiltViewModel
class ImageConfirmationViewModel @Inject constructor(
    @Named("observeOnScheduler") observeOnScheduler: Scheduler,
    @Named("subscribeOnScheduler") subscribeOnScheduler: Scheduler,
    private val repository: FaceDataRepository,
    private val creator: RequestBodyCreator
) : BaseViewModel(observeOnScheduler, subscribeOnScheduler) {

    var imageUrl = ObservableField<String>()
    var isShowProgress = ObservableField<Boolean>()
    val score: BehaviorSubject<FaceScore> = BehaviorSubject.create()

    fun detectFace() {
        val url = imageUrl.get() ?: dummyUrl
        val binaryData = creator.create(url) ?: return

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
