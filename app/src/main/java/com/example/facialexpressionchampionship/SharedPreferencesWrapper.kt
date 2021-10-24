package com.example.facialexpressionchampionship

import android.content.Context
import javax.inject.Inject

open class SharedPreferencesWrapper @Inject constructor(
    context: Context
) {

    private val HISTORY_KEY = "history_key"
    private val BATTLE_ID = "battle_id"

    private val sharedPreferences = context.getSharedPreferences(HISTORY_KEY, Context.MODE_PRIVATE)

    fun saveBattleId(id: Int) {
        try {
            sharedPreferences
                .edit()
                .putInt(BATTLE_ID, id + 1)
                .apply()
        } catch (e: NullPointerException) {
            println(e)
        }
    }

    fun getBattleId(): Int {
        return try {
            sharedPreferences.getInt(BATTLE_ID, 1)
        } catch (e: NullPointerException) {
            println(e)
            1
        }
    }

}
