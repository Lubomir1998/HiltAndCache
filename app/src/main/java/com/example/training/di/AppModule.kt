package com.example.training.di

import android.content.Context
import androidx.room.Room
import com.example.training.room.DBHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, DBHelper::class.java, "DataBaseName"
    ).build()

    @Singleton
    @Provides
    fun provideDao(db: DBHelper) = db.getVideoDao()

}