package com.example.facialexpressionchampionship.di

import android.content.Context
import com.example.facialexpressionchampionship.SharedPreferencesWrapper
import com.example.facialexpressionchampionship.data.room.BattleHistoryDao
import com.example.facialexpressionchampionship.data.room.BattleHistoryDatabase
import com.example.facialexpressionchampionship.viewmodel.battle.RequestBodyCreator
import com.example.facialexpressionchampionship.viewmodel.battle.RequestBodyCreatorImpl
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

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BattleHistoryDatabase {
        return BattleHistoryDatabase.getInstance(context)
    }

    @Provides
    fun provideBattleHistoryDao(database: BattleHistoryDatabase): BattleHistoryDao {
        return database.battleHistoryDao
    }

    @Provides
    fun provideSharedPreferencesWrapper(@ApplicationContext context: Context): SharedPreferencesWrapper {
        return SharedPreferencesWrapper(context)
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
