package com.learn.wallpapers_kotlin.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.learn.wallpapers_kotlin.data.WallpapersRepositoryInterFace
import com.learn.wallpapers_kotlin.data.database.FavoritesEntity
import com.learn.wallpapers_kotlin.models.Images
import com.learn.wallpapers_kotlin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WallpapersViewModel @Inject constructor(
    private val wallpapersRepositoryInterFace: WallpapersRepositoryInterFace,
    application: Application
):AndroidViewModel(application) {

    private val wallpapersList = MutableLiveData<Resource<Images>>()
    val wallpapers : LiveData<Resource<Images>>
        get() = wallpapersList

    val readFavoriteWallpapers: LiveData<List<FavoritesEntity>> = wallpapersRepositoryInterFace.readFavoriteWallpapers().asLiveData()


    fun insertFavoriteWallpapers(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            wallpapersRepositoryInterFace.insertFavoriteWallpapers(favoritesEntity)
        }

    fun deleteFavoriteWallpapers(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            wallpapersRepositoryInterFace.deleteFavoriteWallpapers(favoritesEntity)
        }

    fun deleteAllFavoriteWallpapers() =
        viewModelScope.launch(Dispatchers.IO) {
            wallpapersRepositoryInterFace.deleteAllFavoriteWallpapers()
        }


    fun getWallpapers(c:String) = viewModelScope.launch {
        getWallpapersSafeCall(c)
    }


    private suspend fun getWallpapersSafeCall(c: String) {
        if (hasInternetConnection()){
            try {
                val result = wallpapersRepositoryInterFace.getAll(c)
                withContext(Dispatchers.Main){
                    result.data?.let {
                        wallpapersList.value = result
                    }
                }
            }catch (e:Exception){
                wallpapersList.value = Resource.Error("Wallpapers not found.")
            }
        } else {
            wallpapersList.value= Resource.Error("No Internet Connection.")
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}