package com.example.dwitchapp.model.news

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class News (
    val id: Long,
    val documentId: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val medias: List<Media>
)