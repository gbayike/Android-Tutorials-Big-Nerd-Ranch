package com.olugbayike.android.photogallery

import android.opengl.GLES30
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.olugbayike.android.photogallery.api.GalleryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val TAG = "PhotoGalleryViewModel"

class PhotoGalleryViewModel
    : ViewModel(){
    private val photoRepository = PhotoRepository()

    private val _galleryItems: MutableStateFlow<List<GalleryItem>> =
        MutableStateFlow(emptyList())
    val galleryItems: StateFlow<List<GalleryItem>>
        get() = _galleryItems.asStateFlow()

    val flow = photoRepository.fetchPhotos().cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            try {
                val items = photoRepository.fetchPhotos()

//                Log.d(TAG, "Item recieved: $items")
//                _galleryItems.value = items
            }catch (ex: Exception){
                Log.e(TAG,"Failed to fetch gallery items", ex)
            }
        }
    }
}