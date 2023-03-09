package com.learn.wallpapers_kotlin.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.learn.wallpapers_kotlin.R
import com.learn.wallpapers_kotlin.data.database.FavoritesEntity
import com.learn.wallpapers_kotlin.databinding.ActivityDetailsBinding
import com.learn.wallpapers_kotlin.util.Constants.IMAGE
import com.learn.wallpapers_kotlin.viewmodel.WallpapersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()
    private lateinit var wallpapersViewModel: WallpapersViewModel
    private var  jobShareButton : Job? = null
    private var wallpaperSaved = false
    private var savedwallpaperId = 0
    private lateinit var menuItem: MenuItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        wallpapersViewModel = ViewModelProvider(this)[WallpapersViewModel::class.java]
        val largeImageURL = args.result.largeImageURL
        val likes = args.result.likes
        val downloads = args.result.downloads
        val userImageURL = args.result.userImageURL
        val user = args.result.user
        
        binding.wallpaperView.load(largeImageURL)
        binding.likeTextView.text = likes.toString()
        binding.downloadTextView.text = downloads.toString()
        binding.userImage.load(userImageURL)
        binding.UserName.text = user

        binding.shareButton.setOnClickListener {
            shareButton()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        menuItem = menu!!.findItem(R.id.save_favorites_menu)
        checkWallpapers(menuItem)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_favorites_menu && !wallpaperSaved) {
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_favorites_menu && wallpaperSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkWallpapers(menuItem: MenuItem) {
        wallpapersViewModel.readFavoriteWallpapers.observe(this){ favorites ->
            try {
                for (savedWallpaper in favorites){
                    if (savedWallpaper.result.id == args.result.id){
                        menuItemColor(menuItem,R.color.yellow)
                        savedwallpaperId = savedWallpaper.id
                        wallpaperSaved = true
                    }
                }
            }catch (e:Exception){
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }


    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity =
            FavoritesEntity(
                0,
                args.result
            )
        wallpapersViewModel.insertFavoriteWallpapers(favoritesEntity)
        menuItemColor(item, R.color.yellow)
        showSnackBar("Wallpaper saved.")
        wallpaperSaved = true
    }


    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity =
            FavoritesEntity(
                savedwallpaperId,
                args.result
            )
        wallpapersViewModel.deleteFavoriteWallpapers(favoritesEntity)
        menuItemColor(item, R.color.white)
        showSnackBar("Removed from Favorites.")
        wallpaperSaved = false
    }

    private fun showSnackBar(message : String) {
        Snackbar.make(binding.detailsLayout, message, Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    private fun menuItemColor(menuItem: MenuItem, color: Int) {
        menuItem.icon?.setTint(ContextCompat.getColor(this, color))
    }

    private fun shareButton() {
        jobShareButton?.cancel()
        jobShareButton = lifecycleScope.launch {
            try {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,
                        IMAGE + args.result.largeImageURL
                        )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, null)
                startActivity(shareIntent)
            }catch (e:Exception){
                Log.d("Share Button",e.message.toString())
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        menuItemColor(menuItem, R.color.white)
        jobShareButton = null
    }

}