package com.learn.wallpapers_kotlin.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.learn.wallpapers_kotlin.R
import com.learn.wallpapers_kotlin.adapter.WallpapersAdapter
import com.learn.wallpapers_kotlin.databinding.FragmentListBinding
import com.learn.wallpapers_kotlin.util.Resource
import com.learn.wallpapers_kotlin.viewmodel.WallpapersViewModel


class ListFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var wallPapersViewModel:WallpapersViewModel
    private val wAdapter by lazy { WallpapersAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       wallPapersViewModel = ViewModelProvider(requireActivity())[WallpapersViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list,container,false)
        binding.lifecycleOwner = this
        binding.wallpapersViewModel = wallPapersViewModel
        setupRecyclerView()
        searchData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.list_menu, menu)
                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as? SearchView
                searchView?.setOnQueryTextListener(this@ListFragment)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = wAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),1)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchImage(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun searchImage(c:String){
        wallPapersViewModel.getWallpapers(c)

    }

    private fun searchData() {
        wallPapersViewModel.wallpapers.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    val url = response.data
                    url?.let { wAdapter.setData(it) }
                }
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}