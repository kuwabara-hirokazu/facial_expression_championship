package com.example.facialexpressionchampionship.data

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ThemeRepository @Inject constructor(
    private val localData: LocalData
): ThemeSource {

    override fun getTheme(): Single<Int> {
        localData.changeTheme()
        return Single.just(localData.getTheme())
    }
}
