package com.olugbayike.android.photogallery.api

import com.olugbayike.android.photogallery.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = BuildConfig.API_KEY
interface FlickrApi {


    @GET(
            "services/rest/?method=flickr.interestingness.getList" +
            "&api_key=$API_KEY" +
            "&format=json" +
            "&nojsoncallback=1" +
            "&extras=url_s"
    )
    suspend fun fetchPhotos(
        @Query("page") page: Int
    ): FlickrResponse
}