package com.learn.wallpapers_kotlin.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.learn.wallpapers_kotlin.R
import com.learn.wallpapers_kotlin.models.Result
import com.learn.wallpapers_kotlin.view.ListFragmentDirections


class ListRowBinding {

    companion object {

        @BindingAdapter("onWallpaperClickListener")
        @JvmStatic
        fun onWallpaperClickListener(listRowLayout: ConstraintLayout, result: Result) {
            listRowLayout.setOnClickListener {
                try {
                    val action =
                        ListFragmentDirections.actionListFragmentToDetailsActivity(result)
                    listRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onWallpaperClickListener", e.message.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(500)
                error(R.drawable.ic_error)
            }
        }

    }
}