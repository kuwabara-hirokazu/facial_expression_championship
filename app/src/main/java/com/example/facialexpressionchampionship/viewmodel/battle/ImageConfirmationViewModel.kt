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
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ImageConfirmationViewModel @Inject constructor(
    @Named("observeOnScheduler") observeOnScheduler: Scheduler,
    @Named("subscribeOnScheduler") subscribeOnScheduler: Scheduler,
    private val repository: FaceDataRepository
) : BaseViewModel(observeOnScheduler, subscribeOnScheduler) {

    private val MEDEA_TYPE = "application/octet-stream"

    var imageUrl = ObservableField<String>()
    var isShowProgress = ObservableField<Boolean>()
    val score: BehaviorSubject<FaceScore> = BehaviorSubject.create()

    fun detectFace() {
//        val byte = File(imageUrl.get()).toByteArray() ?: return
        val byte = File("https://ranking.xgoo.jp/image_proxy/resize/w_282_h_282/tool/images/talent/2000072988.jpg?pos=4").toByteArray() ?: return

        val binaryData =
            byte.toRequestBody(MEDEA_TYPE.toMediaTypeOrNull(), 0, byte.size)

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
