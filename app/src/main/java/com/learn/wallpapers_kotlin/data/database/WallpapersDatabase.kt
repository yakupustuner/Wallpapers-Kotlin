package com.learn.wallpapers_kotlin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [FavoritesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(WallpapersTypeConverter::class)
abstract class WallpapersDatabase:RoomDatabase() {
abstract fun wallpapersDao():WallpapersDao
}