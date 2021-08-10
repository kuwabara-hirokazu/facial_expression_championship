package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.ApiRequest
import com.example.facialexpressionchampionship.model.FaceResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val api: ApiRequest
) {
    fun detectFace(url: MutableMap<String, String>): Single<Array<FaceResponse>> {
        return api.detectFace(url)
    }
}
