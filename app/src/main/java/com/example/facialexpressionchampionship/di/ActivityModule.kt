package com.example.facialexpressionchampionship.di

import android.content.Context
import com.example.facialexpressionchampionship.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import java.io.File

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideFile(@ActivityContext context: Context): File {
        // ストレージ/Android/media にディレクトリ作成
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else context.filesDir
    }
}
