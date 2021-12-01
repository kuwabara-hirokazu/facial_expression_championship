package com.example.facialexpressionchampionship.data.source.local

interface SharedPreferencesData {

    fun saveBattleId(id: Int)

    fun getBattleId(): Int
}
