package com.learn.wallpapers_kotlin.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface WallpapersDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteWallpapers(favoritesEntity: FavoritesEntity)


    @Query("SELECT * FROM favorite_wallpapers_table ORDER BY id ASC")
    fun readFavoriteWallpapers(): Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteWallpapers(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_wallpapers_table")
    suspend fun deleteAllFavoriteRecipes()

}