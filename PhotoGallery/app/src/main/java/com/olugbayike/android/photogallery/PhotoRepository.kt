package com.olugbayike.android.photogallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.olugbayike.android.photogallery.api.FlickrApi
import com.olugbayike.android.photogallery.api.FlickrResponse
import com.olugbayike.android.photogallery.api.GalleryItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create


class PhotoRepository {
    private val flickrApi: FlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        flickrApi = retrofit.create()
    }

//    suspend fun fetchPhotos(): List<GalleryItem> = flickrApi.fetchPhotos().photos.galleryItem
    fun fetchPhotos(): Flow<PagingData<GalleryItem>> {
        return Pager(
                config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
                )
//            ),
//            pagingSourceFactory = { FlickrPagingSource(flickrApi)}
        ){
            FlickrPagingSource(flickrApi)
        }.flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}
