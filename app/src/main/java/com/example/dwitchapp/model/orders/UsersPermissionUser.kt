package com.example.dwitchapp.model.orders

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsersPermissionsUser (
    val id: Long,
    val documentId: String,
    val username: String,
    val email: String,
    val provider: String,
    val confirmed: Boolean,
    val blocked: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String
)