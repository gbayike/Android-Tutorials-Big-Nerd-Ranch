package com.olugbayike.android.photogallery.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlickrResponse(
    val photos: PhotoResponse
)

//data class ResponseItems<FlickrResponse>(
//    @field:Json(name = "results") val results: List<FlickrResponse>
//)
