package com.example.facialexpressionchampionship.viewmodel.battle

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.data.repository.FaceDataRepository
import com.example.facialexpressionchampionship.data.repository.RequestBodyCreator
import com.example.facialexpressionchampionship.model.FaceScore
import com.example.facialexpressionchampionship.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

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
        val url = imageUrl.get() ?: return
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
