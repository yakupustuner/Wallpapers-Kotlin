package com.learn.wallpapers_kotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.learn.wallpapers_kotlin.R
import com.learn.wallpapers_kotlin.adapter.FavoriteWallpapersAdapter
import com.learn.wallpapers_kotlin.databinding.FragmentFavoriteWallpapersBinding
import com.learn.wallpapers_kotlin.viewmodel.WallpapersViewModel


class FavoriteWallpapersFragment : Fragment() {
    private var _binding : FragmentFavoriteWallpapersBinding? = null
    private val binding get() = _binding!!
   private lateinit var wallpapersViewModel: WallpapersViewModel
   private val fAdapter:FavoriteWallpapersAdapter by lazy {
       FavoriteWallpapersAdapter(
           requireActivity(),
           wallpapersViewModel
       )
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wallpapersViewModel = ViewModelProvider(requireActivity())[WallpapersViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite_wallpapers,container,false)
        binding.lifecycleOwner = this
        binding.wallpapersViewModel = wallpapersViewModel
        binding.fAdapter = fAdapter
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.favorite_delete_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.delete_favorite_wallpaper_menu) {
                    wallpapersViewModel.deleteAllFavoriteWallpapers()
                    showSnackBar()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewFavorites.adapter = fAdapter
        binding.recyclerViewFavorites.layoutManager = GridLayoutManager(requireContext(),1)
    }

    private fun showSnackBar() {
        Snackbar.make(binding.root, "All wallpapers removed.",
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        fAdapter.clearActionMode()
    }

}