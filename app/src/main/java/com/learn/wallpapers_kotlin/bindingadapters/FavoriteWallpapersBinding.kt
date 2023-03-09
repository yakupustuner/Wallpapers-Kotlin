package com.learn.wallpapers_kotlin.bindingadapters

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.learn.wallpapers_kotlin.adapter.FavoriteWallpapersAdapter
import com.learn.wallpapers_kotlin.data.database.FavoritesEntity

class FavoriteWallpapersBinding {

    companion object {

        @BindingAdapter("setVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setVisibility(view: View, favoritesEntity: List<FavoritesEntity>?, fAdapter: FavoriteWallpapersAdapter?) {
            when (view) {
                is RecyclerView -> {
                    val data = favoritesEntity.isNullOrEmpty()
                    view.isInvisible = data
                    if(!data){
                        favoritesEntity?.let { fAdapter?.setData(it) }
                    }
                }
                else -> view.isVisible = favoritesEntity.isNullOrEmpty()
            }
        }

    }
}