package com.example.facialexpressionchampionship

import android.content.SharedPreferences
import com.example.facialexpressionchampionship.data.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesRepository {

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
