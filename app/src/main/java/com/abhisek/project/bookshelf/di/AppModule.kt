package com.abhisek.project.bookshelf.di

import android.content.Context
import com.abhisek.project.bookshelf.data.local.dao.BookShelfDao
import com.abhisek.project.bookshelf.data.local.database.BookShelfDatabase
import com.abhisek.project.bookshelf.data.local.repository.LocalRepoImpl
import com.abhisek.project.bookshelf.data.remote.repository.RemoteRepoImpl
import com.abhisek.project.bookshelf.data.remote.service.BookShelfApiService
import com.abhisek.project.bookshelf.domain.repository.ILocalRepo
import com.abhisek.project.bookshelf.domain.repository.IRemoteRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalRepo(
        @ApplicationContext context: Context,
        bookShelfDao: BookShelfDao
    ): ILocalRepo {
        return LocalRepoImpl(context, bookShelfDao)
    }

    @Provides
    @Singleton
    fun provideDao(bookShelfDatabase: BookShelfDatabase): BookShelfDao {
        return bookShelfDatabase.bookShelfDao
    }

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): BookShelfDatabase {
        return BookShelfDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): BookShelfApiService {
        return retrofit.create(BookShelfApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepo(remoteRepoImpl: RemoteRepoImpl) : IRemoteRepo {
        return remoteRepoImpl
    }

}