package com.example.facialexpressionchampionship.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BattleInformationEntity::class, ChallengerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BattleHistoryDatabase : RoomDatabase() {

    abstract val battleHistoryDao: BattleHistoryDao

    companion object {
        private const val DATABASE_NAME = "battle.db"

        @Volatile
        private var INSTANCE: BattleHistoryDatabase? = null

        fun getInstance(context: Context): BattleHistoryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BattleHistoryDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
