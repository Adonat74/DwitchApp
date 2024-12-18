package com.example.dwitchapp.model

import com.example.dwitchapp.model.Ingredient
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@JsonClass(generateAdapter = true)
data class Order (
    val id: Int,
    val documentId: String?,
    val placedAt: String?,
    val receivedAt: String?,
    val cookMessage: String?,
    val price: Int,
    val progress: Int,
    val createdAt: String?,
    val updatedAt: String?,
    val publishedAt: String?,
    val ingredients: List<Ingredient>,
    //val usersPermissionsUser: UsersPermissionsUser?,
    val users_permissions_user: UsersPermissionsUser?,
    val store: Store?
) {
    fun getToppingCount(): Int {
        return ingredients.count{it.kind == "topping"}
    }
    fun getMainCount(): Int {
        return ingredients.count{it.kind == "main"}
    }

    fun getFormatedPlacedAtDate(): String {
        val instant = Instant.parse(placedAt)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm", Locale.FRANCE)
        val dateTime = instant.atZone(ZoneId.of("Europe/Paris")).toLocalDateTime()
        return dateTime.format(formatter)
    }
}