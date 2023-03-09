package com.learn.wallpapers_kotlin.adapter

import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.learn.wallpapers_kotlin.R
import com.learn.wallpapers_kotlin.data.database.FavoritesEntity
import com.learn.wallpapers_kotlin.databinding.FavoriteWallpapersRowLayoutBinding
import com.learn.wallpapers_kotlin.util.WallpapersDiffUtil
import com.learn.wallpapers_kotlin.viewmodel.WallpapersViewModel

class FavoriteWallpapersAdapter(
    private val fragmentActivity: FragmentActivity,
    private val wallpapersViewModel: WallpapersViewModel
):RecyclerView.Adapter<FavoriteWallpapersAdapter.FavoriteViewHolder>(),ActionMode.Callback {

    private var multiSelection = false
    private lateinit var fActionMode: ActionMode
    private lateinit var root: View

    private var selectedWallpaper = arrayListOf<FavoritesEntity>()
    private var favoriteViewHolder = arrayListOf<FavoriteViewHolder>()
    private var favoriteWallpaper = emptyList<FavoritesEntity>()

    class FavoriteViewHolder(val binding: FavoriteWallpapersRowLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }
        }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FavoriteWallpapersRowLayoutBinding>(inflater, R.layout.favorite_wallpapers_row_layout,parent,false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        favoriteViewHolder.add(holder)
        root = holder.itemView.rootView
        val currentWallpaper = favoriteWallpaper[position]
        holder.bind(currentWallpaper)

        saveItemState(currentWallpaper,holder)


        holder.binding.favoriteWallpapersRowLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                fragmentActivity.startActionMode(this)
                wallpaperSelection(holder, currentWallpaper)
                true
            } else {
                wallpaperSelection(holder, currentWallpaper)
                true
            }

        }
    }

    private fun wallpaperSelection(
        holder: FavoriteViewHolder,
        currentWallpaper: FavoritesEntity
    ) {
        if (selectedWallpaper.contains(currentWallpaper)){
            selectedWallpaper.remove(currentWallpaper)
            changeWallpaperStyle(holder, R.color.white,R.color.white)
            actionModeTitle()
        }else {
            selectedWallpaper.add(currentWallpaper)
            changeWallpaperStyle(holder,R.color.purple_500,R.color.black)
            actionModeTitle()
        }
    }

    private fun actionModeTitle() {
        when(selectedWallpaper.size){
            0 -> {
                fActionMode.finish()
                multiSelection = false
            }
            1 -> {
                fActionMode.title= "${selectedWallpaper.size} item selected"
            }
            else ->  {
                fActionMode.title = "${selectedWallpaper.size} items selected"
            }
        }
    }

    private fun changeWallpaperStyle(
        holder: FavoriteViewHolder,
        color: Int,
        white: Int
    ) {
        holder.binding.favoriteWallpapersRowLayout.setBackgroundColor(
            ContextCompat.getColor(fragmentActivity,color)
        )
        holder.binding.cardView.strokeColor =
            ContextCompat.getColor(fragmentActivity,white)
    }

    private fun saveItemState(
        currentWallpaper: FavoritesEntity,
        holder: FavoriteViewHolder
    ) {
        if (selectedWallpaper.contains(currentWallpaper)){
            changeStyle(holder, R.color.purple_500, R.color.black)
        }else{
            changeStyle(holder,R.color.white,R.color.white)
        }
    }

    private fun changeStyle(
        holder: FavoriteViewHolder,
        color: Int,
        black: Int
    ) {
        holder.binding.favoriteWallpapersRowLayout.setBackgroundColor(
            ContextCompat.getColor(fragmentActivity,color)
        )
        holder.binding.cardView.strokeColor =
            ContextCompat.getColor(fragmentActivity,black)
    }

    override fun getItemCount(): Int {
        return favoriteWallpaper.size
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorite_delete_menu,menu)
        fActionMode = actionMode!!
        statusBarColor(R.color.purple_500)
        return true
    }


    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_wallpaper_menu){
            selectedWallpaper.forEach {
                wallpapersViewModel.deleteFavoriteWallpapers(it)
            }
            showSnackBar("${selectedWallpaper.size} Wallpaper/s removed.")
            multiSelection = false
            selectedWallpaper.clear()
            actionMode?.finish()
        }
        return true
    }


    override fun onDestroyActionMode(actionMode: ActionMode?) {
      favoriteViewHolder.forEach { holder ->
          changeStyle(holder,R.color.white,R.color.white)
      }
        multiSelection = false
        selectedWallpaper.clear()
        statusBarColor(R.color.purple_500)
    }

    private fun statusBarColor(color: Int) {
       fragmentActivity.window.statusBarColor =
           ContextCompat.getColor(fragmentActivity,color)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    fun setData(newFavoriteWallpaper: List<FavoritesEntity>) {
        val favoriteWallpaperDiffUtil =
            WallpapersDiffUtil(favoriteWallpaper, newFavoriteWallpaper)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteWallpaperDiffUtil)
        favoriteWallpaper = newFavoriteWallpaper
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearActionMode() {
        if (this::fActionMode.isInitialized) {
            fActionMode.finish()
        }
    }
}