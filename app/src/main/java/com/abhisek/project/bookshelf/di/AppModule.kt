package com.abhisek.project.bookshelf.di

import android.content.Context
import com.abhisek.project.bookshelf.data.local.repository.LocalRepoImpl
import com.abhisek.project.bookshelf.domain.repository.ILocalRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalRepo(@ApplicationContext context: Context) : ILocalRepo {
        return LocalRepoImpl(context)
    }
}