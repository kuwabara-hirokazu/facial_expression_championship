package com.example.facialexpressionchampionship.viewmodel

import androidx.databinding.ObservableField
import com.example.facialexpressionchampionship.ApiRequest
import com.example.facialexpressionchampionship.FaceResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class ImageConfirmationViewModel @Inject constructor(
    private val api: ApiRequest
) : BaseViewModel() {

    var imageUrl = ObservableField<String>()
    val result = BehaviorSubject.create<FaceResponse>()

    fun detectFace(url: MutableMap<String, String>) {
        api.detectFace(url)
            .execute(
                onSuccess = {
                    result.onNext(it[0])
                }
            )
    }
}
