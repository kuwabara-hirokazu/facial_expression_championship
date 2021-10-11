package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.data.FaceDataRepository
import com.example.facialexpressionchampionship.extension.toByteArray
import com.example.facialexpressionchampionship.model.FaceScore
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ImageConfirmationViewModel @Inject constructor(
    private val repository: FaceDataRepository
) : BaseViewModel() {

    private val MEDEA_TYPE = "application/octet-stream"

    var imageUrl = ObservableField<String>()
    val score: BehaviorSubject<FaceScore> = BehaviorSubject.create()

    fun detectFace() {
        val byte = File(imageUrl.get()).toByteArray() ?: return

        val binaryData =
            byte.toRequestBody(MEDEA_TYPE.toMediaTypeOrNull(), 0, byte.size)

        repository.detectFace(binaryData)
            .execute(
                onSuccess = {
                    Timber.d(it.toString())
                    score.onNext(it)
                },
                retry = { detectFace() }
            )
    }
}
