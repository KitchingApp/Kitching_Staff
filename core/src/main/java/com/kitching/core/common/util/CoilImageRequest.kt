package com.kitching.core.common.util

import com.kitching.core.R
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.fallback
import coil3.request.placeholder
import coil3.size.Size

class CoilImageRequest {
    companion object {
        fun getImageRequest(
            sourceImage: String,
            placeholder: Int = R.drawable.icon_placeholder,
            errorImage: Int = R.drawable.icon_error_image,
            fallback: Int = R.drawable.logo_dish
        ): ImageRequest {
            return ImageRequest.Builder(CoilManager.getContext())
                .data(sourceImage)
                .size(Size.ORIGINAL)
                .placeholder(placeholder)
                .error(errorImage)
                .fallback(fallback)
                .crossfade(true)
                .build()
        }
    }
}