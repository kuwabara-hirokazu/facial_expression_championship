package com.example.facialexpressionchampionship.data.source.local

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesDataImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesData {

    private val BATTLE_ID = "battle_id"

    override fun saveBattleId(id: Int) {
        sharedPreferences
            .edit()
            .putInt(BATTLE_ID, id + 1)
            .apply()
    }

    override fun getBattleId(): Int {
        return sharedPreferences.getInt(BATTLE_ID, 1)
    }

}
