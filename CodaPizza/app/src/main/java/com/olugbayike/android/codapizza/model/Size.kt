package com.olugbayike.android.codapizza.model

import androidx.annotation.StringRes
import com.olugbayike.android.codapizza.R

enum class Size (
    @StringRes val size: Int
){
    Small(
        size = R.string.size_small
    ),

    Medium(
        size = R.string.size_medium
    ),

    Large(
        size = R.string.size_large
    ),

    ExtraLarge(
        size = R.string.size_extra_large
    )
}