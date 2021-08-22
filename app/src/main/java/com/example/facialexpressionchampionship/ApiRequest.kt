package com.example.facialexpressionchampionship

import com.example.facialexpressionchampionship.model.FaceResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiRequest {

    @Headers(
        "Content-Type: application/octet-stream",
        "Ocp-Apim-Subscription-Key: 19565daf9eef4b368637d70458462ad7"
    )
    @POST("detect?returnFaceAttributes=emotion")
    fun detectFace(
        @Body binaryData: RequestBody
    ): Single<Array<FaceResponse>>
}
