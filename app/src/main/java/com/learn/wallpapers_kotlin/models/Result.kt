package com.learn.wallpapers_kotlin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    @SerializedName("collections")
    val collections: Int,
    @SerializedName("comments")
    val comments: Int,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageHeight")
    val imageHeight: Int,
    @SerializedName("imageSize")
    val imageSize: Int,
    @SerializedName("imageWidth")
    val imageWidth: Int,
    @SerializedName("largeImageURL")
    val largeImageURL: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("pageURL")
    val pageURL: String,
    @SerializedName("previewHeight")
    val previewHeight: Int,
    @SerializedName("previewURL")
    val previewURL: String,
    @SerializedName("previewWidth")
    val previewWidth: Int,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("userImageURL")
    val userImageURL: String,
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("views")
    val views: Int,
    @SerializedName("webformatHeight")
    val webformatHeight: Int,
    @SerializedName("webformatURL")
    val webformatURL: String,
    @SerializedName("webformatWidth")
    val webformatWidth: Int
):Parcelable
