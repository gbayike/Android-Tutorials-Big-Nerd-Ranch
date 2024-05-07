package com.olugbayike.android.photogallery

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.olugbayike.android.photogallery.PhotoRepository.Companion.NETWORK_PAGE_SIZE
import com.olugbayike.android.photogallery.api.FlickrApi
import com.olugbayike.android.photogallery.api.FlickrResponse
import com.olugbayike.android.photogallery.api.GalleryItem
import okio.IOException

const val FLICKR_STARTING_PAGE_INDEX = 1

class FlickrPagingSource (
    private val service: FlickrApi
): PagingSource<Int, GalleryItem>(){

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        val pageIndex = params.key ?: FLICKR_STARTING_PAGE_INDEX

        return try {
            val response = service.fetchPhotos(pageIndex)
            val photos = response.photos.galleryItem

            val nextKey = if (photos.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            return LoadResult.Page(
                data = photos,
                prevKey = if (pageIndex == FLICKR_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        }catch (exception: IOException){
            return LoadResult.Error(exception)
        }catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}