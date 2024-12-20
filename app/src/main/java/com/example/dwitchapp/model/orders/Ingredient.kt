package com.example.dwitchapp.model.orders

import com.example.dwitchapp.ui.theme.IngredientKind
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ingredient (
    val id: Long,
    val documentId: String,
    val name: String,
    val description: String,
    val isVegan: Boolean? = null,
    val isSpicy: Boolean? = null,
    val kind: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String
) {
    fun getEmoji(): String {
        val ingredientKind = IngredientKind.fromString(kind)
        return ingredientKind.emoji
    }

    // Retrieve the color corresponding to the kind
    fun getColor(): String {
        val ingredientKind = IngredientKind.fromString(kind)
        return ingredientKind.color
    }
}