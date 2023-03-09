package com.learn.wallpapers_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.learn.wallpapers_kotlin.R
import com.learn.wallpapers_kotlin.databinding.ListRowLayoutBinding
import com.learn.wallpapers_kotlin.models.Images
import com.learn.wallpapers_kotlin.models.Result
import com.learn.wallpapers_kotlin.util.WallpapersDiffUtil

class WallpapersAdapter : RecyclerView.Adapter<WallpapersAdapter.WallpapersViewHolder>() {
    private var wallpapers = emptyList<Result>()

    class WallpapersViewHolder(var binding:ListRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpapersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ListRowLayoutBinding>(inflater, R.layout.list_row_layout,parent,false)
        return WallpapersViewHolder(view)
    }

    override fun onBindViewHolder(holder: WallpapersViewHolder, position: Int) {
        holder.binding.result = wallpapers[position]
    }

    override fun getItemCount(): Int {
       return wallpapers.size
    }

    fun setData(newData:Images){
        val wallpapersDiffUtil = WallpapersDiffUtil(wallpapers,newData.hits)
        val diffUtilResult = DiffUtil.calculateDiff(wallpapersDiffUtil)
        wallpapers = newData.hits
      diffUtilResult.dispatchUpdatesTo(this)
    }
}