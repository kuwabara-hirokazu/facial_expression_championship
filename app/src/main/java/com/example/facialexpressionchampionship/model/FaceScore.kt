package com.example.facialexpressionchampionship.model

data class FaceScore(
    val anger: String,
    val contempt: String,
    val disgust: String,
    val fear: String,
    val happiness: String,
    val neutral: String,
    val sadness: String,
    val surprise: String
) {
    fun getThemeScoreFrom(theme: ThemeType): String {
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
