package com.example.facialexpressionchampionship.di

import com.example.facialexpressionchampionship.SharedPreferencesRepositoryImpl
import com.example.facialexpressionchampionship.data.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindFaceDataRepository(repository: FaceDataRepositoryImpl): FaceDataRepository

    @Binds
    abstract fun bindBattleHistoryRepository(repository: BattleHistoryRepositoryImpl): BattleHistoryRepository

    @Binds
    abstract fun bindSharedPreferencesRepository(repository: SharedPreferencesRepositoryImpl): SharedPreferencesRepository
}
