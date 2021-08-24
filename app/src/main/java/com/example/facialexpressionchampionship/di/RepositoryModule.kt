package com.example.facialexpressionchampionship.di

import com.example.facialexpressionchampionship.data.FaceDataRepository
import com.example.facialexpressionchampionship.data.FaceDataSource
import com.example.facialexpressionchampionship.data.ThemeRepository
import com.example.facialexpressionchampionship.data.ThemeSource
import com.example.facialexpressionchampionship.data.ScoreCacheRepository
import com.example.facialexpressionchampionship.data.ScoreCacheSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindFaceDataRepository(repository: FaceDataRepository): FaceDataSource

    @Binds
    abstract fun bindThemeRepository(repository: ThemeRepository): ThemeSource
    
    @Binds
    abstract fun bindScoreCacheRepository(repository: ScoreCacheRepository): ScoreCacheSource
}
