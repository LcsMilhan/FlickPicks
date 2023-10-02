package com.lcsmilhan.flickpicks.data.local.mapper

import com.lcsmilhan.flickpicks.domain.local.model.Favorites
import com.lcsmilhan.flickpicks.domain.local.model.WatchList
import com.lcsmilhan.flickpicks.domain.remote.model.Details

fun Details.toFavorites(): Favorites =
    Favorites(
        id = id,
        title = title,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )

fun Details.toWatchList(): WatchList =
    WatchList(
        id = id,
        title = title,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )