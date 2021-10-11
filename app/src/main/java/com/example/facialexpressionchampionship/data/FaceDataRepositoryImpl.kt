package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.extension.floor
import com.example.facialexpressionchampionship.extension.hundredfold
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
                    emotion.anger.hundredfold().floor(),
                    emotion.contempt.hundredfold().floor(),
                    emotion.disgust.hundredfold().floor(),
                    emotion.fear.hundredfold().floor(),
                    emotion.happiness.hundredfold().floor(),
                    emotion.neutral.hundredfold().floor(),
                    emotion.sadness.hundredfold().floor(),
                    emotion.surprise.hundredfold().floor()
                )
            )
        }
    }
}
