package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.extension.hundredfold
import com.example.facialexpressionchampionship.model.FaceScore
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import javax.inject.Inject

class FaceDataRepository @Inject constructor(
    private val remote: RemoteData,
    private val localData: LocalData
) : FaceDataSource {

    override fun detectFace(binaryData: RequestBody): Single<FaceScore> {
        return remote.detectFace(binaryData).flatMap {
            val emotion = it[0].faceAttributes.emotion
            Single.just(
                FaceScore(
                    localData.getThemeScore(emotion).hundredfold(),
                    emotion.anger.hundredfold(),
                    emotion.contempt.hundredfold(),
                    emotion.disgust.hundredfold(),
                    emotion.fear.hundredfold(),
                    emotion.happiness.hundredfold(),
                    emotion.neutral.hundredfold(),
                    emotion.sadness.hundredfold(),
                    emotion.surprise.hundredfold()
                )
            )
        }
    }
}
