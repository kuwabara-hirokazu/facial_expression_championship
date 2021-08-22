package com.example.facialexpressionchampionship.data

import io.reactivex.rxjava3.core.Single

interface ThemeSource {
    fun getTheme(): Single<Int>
}
