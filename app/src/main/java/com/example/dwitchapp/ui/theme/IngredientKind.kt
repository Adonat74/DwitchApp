package com.example.dwitchapp.ui.theme

import androidx.compose.ui.graphics.Color


enum class IngredientKind(val emoji: String, val color: String) {
    MAIN("🍖", "#FA5252"),
    TOPPING("🥗", "#37B24D"),
    BREAD("🍞", "#E8590C"),
    SAUCE("🧂", "#F59F00"),
    UNKNOWN("❓", "#364FC7");

    companion object {
        // Retrieve the enum constant from a string (safe handling)
        fun fromString(kind: String?): IngredientKind {
            return values().find { it.name.equals(kind, ignoreCase = true) } ?: UNKNOWN
        }
    }
}