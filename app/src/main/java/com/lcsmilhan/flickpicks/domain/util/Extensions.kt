package com.lcsmilhan.flickpicks.domain.util

import com.lcsmilhan.flickpicks.data.local.MyListMovie
import com.lcsmilhan.flickpicks.domain.model.Movie

fun Movie.toMyListMovie(addedOn: String): MyListMovie {
    return MyListMovie(
        id = this.id,
        imagePath = this.thumbImage,
        title = this.title,
        releaseData = this.releaseDate
    )
}