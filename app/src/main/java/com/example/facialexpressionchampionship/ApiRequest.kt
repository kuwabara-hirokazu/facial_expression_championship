package com.example.facialexpressionchampionship

import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiRequest {

    @Headers(
        "Content-Type: application/json",
        "Ocp-Apim-Subscription-Key: 19565daf9eef4b368637d70458462ad7"
    )
    @POST("detect?returnFaceAttributes=emotion")
    fun detectFace(
        @Body url: MutableMap<String, String>
    ): Single<Array<FaceResponse>>
}
