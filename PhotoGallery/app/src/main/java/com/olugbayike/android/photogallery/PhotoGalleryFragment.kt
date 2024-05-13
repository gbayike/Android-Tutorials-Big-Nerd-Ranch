package com.olugbayike.android.photogallery

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.olugbayike.android.photogallery.databinding.FragmentPhotoGalleryBinding
import kotlinx.coroutines.launch

private const val TAG = "PhotoGalleryFragment"
class PhotoGalleryFragment: Fragment() {
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "cannot access binding because it is null. Is the view visible?"
        }

    private val photoGalleryViewModel : PhotoGalleryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentPhotoGalleryBinding.inflate(inflater, container, false)
        binding.photoGrid.layoutManager = GridLayoutManager(context, 3)
        (requireActivity() as MainActivity).setSupportActionBar(binding.topAppBar)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val menuHost: MenuHost = requireActivity()
        viewLifecycleOwner.lifecycleScope.launch {
//            try {
//                val response = PhotoRepository().fetchPhotos()
//                Log.d(TAG, "Response received: $response")
//            }catch (ex: Exception){
//                Log.e(TAG, "Failed to fetch gallery items", ex)
//            }
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                photoGalleryViewModel.galleryItems.collect() { items ->
////                    Log.d(TAG, "Response received: $items")
//                    binding.photoGrid.adapter = PhotoListAdapter(items)
//                }
                photoGalleryViewModel.uiState.collect() { state ->
//                    Log.d(TAG, "Response received: $items")
                    binding.photoGrid.adapter = PhotoListAdapter(state.images)
                    binding.searchView.setQuery(state.query, false)
                }
            }
        }

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "QueryTextSubmit: $query")
                photoGalleryViewModel.setQuery(query ?: "")
                // on below line getting current view.

                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "QueryTextChange: $newText")
                return false
            }
        })

        menuHost.addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_photo_gallery, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
//                    R.id.menu_item_search -> {
//                        true
//                    }

                    R.id.menu_item_clear -> {
                        // Handle favorite icon press
                        Log.d(TAG,"CLEAR!!!!!!")
                        photoGalleryViewModel.setQuery("")
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        /**
         * The second way of setting menu item click listener
          */

//        binding.topAppBar.setNavigationOnClickListener {
//            // Handle navigation icon press
//        }
//
//        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.menu_item_search -> {
//                    // Handle edit text press
//                    Log.d(TAG,"Search!!!!!!")
//                    true
//                }
//
//                R.id.menu_item_clear -> {
//                    // Handle favorite icon press
//                    Log.d(TAG,"CLEAR!!!!!!")
//                    true
//                }
//                else -> false
//            }
//        }


//        binding.topAppBar.menu.get(R.id.menu_item_search).title
    }

    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

