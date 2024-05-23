package com.olugbayike.android.photogallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olugbayike.android.photogallery.api.GalleryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "PhotoGalleryViewModel"

class PhotoGalleryViewModel : ViewModel(){
    private val photoRepository = PhotoRepository()

//    private val _galleryItems: MutableStateFlow<List<GalleryItem>> =
//        MutableStateFlow(emptyList())
//    val galleryItems: StateFlow<List<GalleryItem>>
//        get() = _galleryItems.asStateFlow()

    private val _uiState: MutableStateFlow<PhotoGalleryUiState> =
        MutableStateFlow(PhotoGalleryUiState())
    val uiState: StateFlow<PhotoGalleryUiState>
        get() = _uiState.asStateFlow()

    val preferencesRepository = PreferencesRepository.get()

    init {
        viewModelScope.launch {
            preferencesRepository.storedQuery.collectLatest{ storedQuery ->
                try {
//                val items = photoRepository.fetchPhotos()
//                Log.d(TAG, "Item recieved: $items")

//                val items = photoRepository.searchPhotos("bicycle")

//                    val items = fetchGalleryItems("bicycle")

                    val items = fetchGalleryItems(storedQuery)

                    _uiState.update { oldState ->
                        oldState.copy(
                            images = items,
                            query = storedQuery
                        )
                    }
//                    _galleryItems.value = items
                }catch (ex: Exception){
                    Log.e(TAG,"Failed to fetch gallery items", ex)
                }
            }
        }
        viewModelScope.launch {
            preferencesRepository.isPolling.collect{ isPolling ->
                _uiState.update {
                    it.copy(isPolling = isPolling)
                }
            }
        }
    }

    fun setQuery(query: String){
        viewModelScope.launch{
//            _galleryItems.value = fetchGalleryItems(query)
            preferencesRepository.setStoredQuery(query)
        }
    }

    fun toggleIsPolling(){
        viewModelScope.launch{
            preferencesRepository.setPolling(!uiState.value.isPolling)
        }
    }
    private suspend fun fetchGalleryItems(query: String): List<GalleryItem>{
        return if (query.isNotEmpty()){
            photoRepository.searchPhotos(query)
        }else{
            photoRepository.fetchPhotos()
        }
    }
}

data class PhotoGalleryUiState(
    val images: List<GalleryItem> = listOf(),
    val query: String = "",
    val isPolling: Boolean = false,
)