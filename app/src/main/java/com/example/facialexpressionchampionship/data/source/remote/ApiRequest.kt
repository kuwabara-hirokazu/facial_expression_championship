package com.example.facialexpressionchampionship.data.source.remote

import com.example.facialexpressionchampionship.BuildConfig
import com.example.facialexpressionchampionship.model.FaceResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiRequest {

    @Headers("Ocp-Apim-Subscription-Key: ${BuildConfig.FACE_API_KEY}")
    @POST("detect?returnFaceAttributes=emotion")
    fun detectFace(
        @Body binaryData: RequestBody
    ): Single<List<FaceResponse>>
}
