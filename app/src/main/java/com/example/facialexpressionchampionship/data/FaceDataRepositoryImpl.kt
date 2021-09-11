package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.model.FaceScore
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import javax.inject.Inject

class FaceDataRepositoryImpl @Inject constructor(
    private val remote: RemoteData,
) : FaceDataRepository {

    override fun detectFace(binaryData: RequestBody): Single<FaceScore> {
        return remote.detectFace(binaryData).flatMap {
            val emotion = it[0].faceAttributes.emotion
            Single.just(
                FaceScore(
                    emotion.anger,
                    emotion.contempt,
                    emotion.disgust,
                    emotion.fear,
                    emotion.happiness,
                    emotion.neutral,
                    emotion.sadness,
                    emotion.surprise
                )
            )
        }
    }
}
