package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.model.Emotion
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody

interface FaceDataSource {

    fun detectFace(binaryData: RequestBody): Single<Emotion>
}
