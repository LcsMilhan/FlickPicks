package com.lcsmilhan.flickpicks.data.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
