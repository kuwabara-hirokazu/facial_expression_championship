package com.example.facialexpressionchampionship.di

import com.example.facialexpressionchampionship.data.BattleHistoryRepository
import com.example.facialexpressionchampionship.data.BattleHistoryRepositoryImpl
import com.example.facialexpressionchampionship.data.FaceDataRepository
import com.example.facialexpressionchampionship.data.FaceDataRepositoryImpl
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
}
