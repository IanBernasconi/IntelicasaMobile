package com.example.intelicasamobile.model

import androidx.annotation.DrawableRes
import com.example.intelicasamobile.R

enum class RoomType(
    @DrawableRes val imageResourceId: Int,
    val apiName: String = ""
) {
    LIVINGROOM(R.drawable.livingroom, "Living"),
    KITCHEN(R.drawable.kitchen, "Cocina"),
    BATHROOM(R.drawable.bathroom, "Baño"),
    GARDEN(R.drawable.garden, "Patio"),
    BEDROOM(R.drawable.bedroom, "Dormitorio"),
    OTHER(R.drawable.other, "Otro");

    companion object {
        fun getRoomType(apiName: String): RoomType {
            return values().find { it.apiName == apiName } ?: OTHER
        }
    }
}