package com.example.facialexpressionchampionship.di

import android.content.Context
import android.content.SharedPreferences
import com.example.facialexpressionchampionship.data.RequestBodyCreator
import com.example.facialexpressionchampionship.data.RequestBodyCreatorImpl
import androidx.room.Room
import com.example.facialexpressionchampionship.data.room.BattleHistoryDao
import com.example.facialexpressionchampionship.data.room.BattleHistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val DATABASE_NAME = "battle.db"
    private const val HISTORY_KEY = "history_key"

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
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(HISTORY_KEY, Context.MODE_PRIVATE)
    }

    @Provides
    @Named("observeOnScheduler")
    fun provideObserveOnScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @Named("subscribeOnScheduler")
    fun provideSubscribeOnScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    fun provide(): RequestBodyCreator {
        return RequestBodyCreatorImpl()
    }
}
