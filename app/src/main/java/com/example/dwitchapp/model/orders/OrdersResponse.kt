package com.example.dwitchapp.model.orders

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrdersResponse(
    val data: List<Order>
)
