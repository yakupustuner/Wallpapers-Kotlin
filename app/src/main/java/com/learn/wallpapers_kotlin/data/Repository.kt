package com.learn.wallpapers_kotlin.data

import com.learn.wallpapers_kotlin.data.database.FavoritesEntity
import com.learn.wallpapers_kotlin.data.database.WallpapersDao
import com.learn.wallpapers_kotlin.data.network.WallpapersApi
import com.learn.wallpapers_kotlin.models.Images
import com.learn.wallpapers_kotlin.util.Constants.API_KEY
import com.learn.wallpapers_kotlin.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val wallpapersDao:WallpapersDao,
    private val wallpapersApi: WallpapersApi
    ):WallpapersRepositoryInterFace {

    override suspend fun getAll(a: String): Resource<Images> {
        return try {
            val result = wallpapersApi.Search(a,API_KEY,"photo")
            if (result.isSuccessful){
                result.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("Error",null)
            }else{
                Resource.Error("Error",null)
            }
        }catch (e:Exception){
            Resource.Error("Error",null)
        }
    }



    override suspend fun insertFavoriteWallpapers(favoritesEntity: FavoritesEntity) {
       wallpapersDao.insertFavoriteWallpapers(favoritesEntity)
    }

    override suspend fun deleteFavoriteWallpapers(favoritesEntity: FavoritesEntity) {
       wallpapersDao.deleteFavoriteWallpapers(favoritesEntity)
    }

    override suspend fun deleteAllFavoriteWallpapers() {
        wallpapersDao.deleteAllFavoriteRecipes()
    }

    override fun readFavoriteWallpapers(): Flow<List<FavoritesEntity>> {
       return wallpapersDao.readFavoriteWallpapers()
    }




}