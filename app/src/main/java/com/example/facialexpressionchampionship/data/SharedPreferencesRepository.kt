package com.example.facialexpressionchampionship.data

interface SharedPreferencesRepository {

    fun saveBattleId(id: Int)

    fun getBattleId(): Int
}
