package com.example.facialexpressionchampionship.di

import com.example.facialexpressionchampionship.data.source.local.SharedPreferencesDataImpl
import com.example.facialexpressionchampionship.data.repository.BattleHistoryRepository
import com.example.facialexpressionchampionship.data.repository.BattleHistoryRepositoryImpl
import com.example.facialexpressionchampionship.data.repository.FaceDataRepository
import com.example.facialexpressionchampionship.data.repository.FaceDataRepositoryImpl
import com.example.facialexpressionchampionship.data.source.local.SharedPreferencesData
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
    abstract fun bindSharedPreferencesRepository(data: SharedPreferencesDataImpl): SharedPreferencesData
}
