package com.learn.wallpapers_kotlin.data.network

import com.learn.wallpapers_kotlin.models.Images
import com.learn.wallpapers_kotlin.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpapersApi {

    @GET("api/")
    suspend fun Search(
        @Query("q") Query:String,
        @Query("key") Key:String = API_KEY,
        @Query("image_type") imageType:String
    ): Response<Images>
}