package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.R
import com.example.facialexpressionchampionship.model.Emotion
import javax.inject.Inject

class LocalData @Inject constructor(){

    companion object {
        var themeType = ThemeType.values().toList().shuffled().first()
    }

    private fun getThemeId(): Int {
        return themeType.id
    }

    fun getThemeScore(emotion: Emotion): String {
        val emotionList = listOf(
            emotion.anger,
            emotion.contempt,
            emotion.disgust,
            emotion.fear,
            emotion.happiness,
            emotion.neutral,
            emotion.sadness,
            emotion.surprise
        )
        return emotionList[getThemeId()]
    }
}

enum class ThemeType(val id: Int, val theme: Int) {
    ANGER(0, R.string.anger),
    CONTEMPT(1, R.string.contempt),
    DISGUST(2, R.string.disgust),
    FEAR(3, R.string.fear),
    HAPPINESS(4, R.string.happiness),
    NEUTRAL(5, R.string.neutral),
    SADNESS(6, R.string.sadness),
    SURPRISE(7, R.string.surprise)
}
