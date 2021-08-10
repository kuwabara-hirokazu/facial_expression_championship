package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.model.FaceResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FaceDataRepository @Inject constructor(
    private val remote: RemoteData
) : FaceDataSource {

    override fun detectFace(url: MutableMap<String, String>): Single<Array<FaceResponse>> {
        return remote.detectFace(url)
    }
}
