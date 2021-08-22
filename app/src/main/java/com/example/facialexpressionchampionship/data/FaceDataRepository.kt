package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.model.Emotion
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import javax.inject.Inject

class FaceDataRepository @Inject constructor(
    private val remote: RemoteData
) : FaceDataSource {

    override fun detectFace(binaryData: RequestBody): Single<Emotion> {
        return remote.detectFace(binaryData).flatMap {
            Single.just(it[0].faceAttributes.emotion)
        }
    }
}
