package com.learn.wallpapers_kotlin.models

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("hits")
    val hits: List<Result>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)