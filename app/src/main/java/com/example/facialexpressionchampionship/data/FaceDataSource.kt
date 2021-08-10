package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.model.FaceResponse
import io.reactivex.rxjava3.core.Single

interface FaceDataSource {

    fun detectFace(url: MutableMap<String, String>): Single<Array<FaceResponse>>
}
