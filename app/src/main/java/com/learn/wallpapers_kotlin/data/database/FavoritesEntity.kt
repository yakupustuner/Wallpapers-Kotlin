package com.learn.wallpapers_kotlin.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learn.wallpapers_kotlin.util.Constants.FAVORITE_WALLPAPERS_TABLE
import com.learn.wallpapers_kotlin.models.Result

@Entity(tableName = FAVORITE_WALLPAPERS_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var result: Result
)