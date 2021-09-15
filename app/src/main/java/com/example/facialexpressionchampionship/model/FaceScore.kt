package com.example.facialexpressionchampionship.model

import java.io.Serializable

data class FaceScore(
    val anger: Float,
    val contempt: Float,
    val disgust: Float,
    val fear: Float,
    val happiness: Float,
    val neutral: Float,
    val sadness: Float,
    val surprise: Float
) : Serializable {
    fun getThemeScoreFrom(theme: ThemeType): Float {
        return when (theme) {
            ThemeType.ANGER -> anger
            ThemeType.CONTEMPT -> contempt
            ThemeType.DISGUST -> disgust
            ThemeType.FEAR -> fear
            ThemeType.HAPPINESS -> happiness
            ThemeType.NEUTRAL -> neutral
            ThemeType.SADNESS -> sadness
            ThemeType.SURPRISE -> surprise
        }
    }
}
