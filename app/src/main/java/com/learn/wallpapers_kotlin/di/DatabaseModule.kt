package com.learn.wallpapers_kotlin.di

import android.content.Context
import androidx.room.Room
import com.learn.wallpapers_kotlin.data.Repository
import com.learn.wallpapers_kotlin.data.WallpapersRepositoryInterFace
import com.learn.wallpapers_kotlin.data.database.WallpapersDao
import com.learn.wallpapers_kotlin.data.database.WallpapersDatabase
import com.learn.wallpapers_kotlin.data.network.WallpapersApi
import com.learn.wallpapers_kotlin.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,WallpapersDatabase::class.java,DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(database: WallpapersDatabase) = database.wallpapersDao()

    @Singleton
    @Provides
    fun injectRepo(dao : WallpapersDao, api: WallpapersApi) = Repository(dao,api) as WallpapersRepositoryInterFace

}