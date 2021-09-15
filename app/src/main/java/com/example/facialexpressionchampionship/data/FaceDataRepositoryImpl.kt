package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.extension.hundredfoldToFloat
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
                    emotion.anger.hundredfoldToFloat(),
                    emotion.contempt.hundredfoldToFloat(),
                    emotion.disgust.hundredfoldToFloat(),
                    emotion.fear.hundredfoldToFloat(),
                    emotion.happiness.hundredfoldToFloat(),
                    emotion.neutral.hundredfoldToFloat(),
                    emotion.sadness.hundredfoldToFloat(),
                    emotion.surprise.hundredfoldToFloat()
                )
            )
        }
    }
}
