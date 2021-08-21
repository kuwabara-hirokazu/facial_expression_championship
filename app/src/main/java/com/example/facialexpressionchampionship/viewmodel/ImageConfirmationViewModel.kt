package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.data.FaceDataSource
import com.example.facialexpressionchampionship.model.Emotion
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import okhttp3.RequestBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ImageConfirmationViewModel @Inject constructor(
    private val repository: FaceDataSource
) : BaseViewModel() {

    var imageUrl = ObservableField<String>()
    val result = BehaviorSubject.create<Emotion>()

    fun detectFace(binaryData: RequestBody) {
        repository.detectFace(binaryData)
            .execute(
                onSuccess = {
                    Timber.d(it.toString())
                    result.onNext(it)
                },
                retry = { detectFace(binaryData) }
            )
    }
}
