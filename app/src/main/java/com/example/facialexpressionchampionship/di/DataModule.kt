package com.example.facialexpressionchampionship.di

import android.content.Context
import androidx.room.Room
import com.example.facialexpressionchampionship.SharedPreferencesWrapper
import com.example.facialexpressionchampionship.data.room.BattleHistoryDao
import com.example.facialexpressionchampionship.data.room.BattleHistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val DATABASE_NAME = "battle.db"

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BattleHistoryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BattleHistoryDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBattleHistoryDao(database: BattleHistoryDatabase): BattleHistoryDao {
        return database.battleHistoryDao
    }

    @Provides
    fun provideSharedPreferencesWrapper(@ApplicationContext context: Context): SharedPreferencesWrapper {
        return SharedPreferencesWrapper(context)
    }
}
