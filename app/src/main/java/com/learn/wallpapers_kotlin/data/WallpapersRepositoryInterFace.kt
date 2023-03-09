package com.learn.wallpapers_kotlin.data

import com.learn.wallpapers_kotlin.data.database.FavoritesEntity
import com.learn.wallpapers_kotlin.models.Images
import com.learn.wallpapers_kotlin.util.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow

@ActivityRetainedScoped
interface WallpapersRepositoryInterFace {

    suspend fun insertFavoriteWallpapers(favoritesEntity: FavoritesEntity)
    suspend fun deleteFavoriteWallpapers(favoritesEntity: FavoritesEntity)
    suspend fun deleteAllFavoriteWallpapers()
    suspend fun getAll(a:String): Resource<Images>
    fun readFavoriteWallpapers():Flow<List<FavoritesEntity>>

}