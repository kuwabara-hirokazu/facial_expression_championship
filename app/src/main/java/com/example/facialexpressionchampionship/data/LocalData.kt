package com.example.facialexpressionchampionship.data

import com.example.facialexpressionchampionship.R
import javax.inject.Inject

class LocalData @Inject constructor(){

    companion object {
        private val themeList = listOf(
            R.string.anger,
            R.string.contempt,
            R.string.disgust,
            R.string.fear,
            R.string.happiness,
            R.string.neutral,
            R.string.sadness,
            R.string.surprise
        )

        var theme = themeList.shuffled()[0]
    }

    fun getTheme(): Int {
        return theme
    }

    fun changeTheme() {
        theme = themeList.shuffled()[0]
    }
}
