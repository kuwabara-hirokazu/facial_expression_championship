package com.example.facialexpressionchampionship

import android.content.Context
import javax.inject.Inject

class SharedPreferencesWrapper @Inject constructor(
    context: Context
) {

    private val HISTORY_KEY = "history_key"
    private val BATTLE_ID = "battle_id"

    private val sharedPreferences = context.getSharedPreferences(HISTORY_KEY, Context.MODE_PRIVATE)

    fun saveBattleId(id: Int) {
        sharedPreferences
            .edit()
            .putInt(BATTLE_ID, id + 1)
            .apply()
    }

    fun getBattleId(): Int {
        return sharedPreferences.getInt(BATTLE_ID, 1)
    }

}
