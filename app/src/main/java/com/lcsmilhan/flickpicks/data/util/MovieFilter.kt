package com.lcsmilhan.flickpicks.data.util

sealed class MovieFilter(val orderType: OrderType) {
    class Genre(orderType: OrderType): MovieFilter(orderType)

    fun copy(orderType: OrderType): MovieFilter {
        return when(this) {
            is Genre -> Genre(orderType)
        }
    }
}
