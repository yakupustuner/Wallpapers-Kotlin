package com.learn.wallpapers_kotlin.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.learn.wallpapers_kotlin.models.Images
import com.learn.wallpapers_kotlin.util.Resource

class ListBinding {


    companion object {

    @BindingAdapter("apiResponse", requireAll = true)
    @JvmStatic
    fun apiResponse(
        view:View,
        response: Resource<Images>?
    ){
        when(view){
            is ImageView -> {
               view.isVisible =  response is Resource.Error
            }
            is TextView -> {
                view.isVisible = response is Resource.Error
                view.text = response?.message.toString()
            }
        }
    }
    }

}