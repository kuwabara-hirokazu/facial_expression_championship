package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.model.FaceScore
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody

interface FaceDataRepository {
    fun detectFace(binaryData: RequestBody): Single<FaceScore>
}
