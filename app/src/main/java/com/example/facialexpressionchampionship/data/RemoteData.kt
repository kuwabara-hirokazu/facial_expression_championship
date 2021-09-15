package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.ApiRequest
import com.example.facialexpressionchampionship.model.FaceResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val api: ApiRequest
) {
    fun detectFace(binaryData: RequestBody): Single<List<FaceResponse>> {
        return api.detectFace(binaryData)
    }
}
