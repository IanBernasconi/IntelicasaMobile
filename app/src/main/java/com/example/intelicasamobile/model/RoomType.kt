package com.example.intelicasamobile.model

import androidx.annotation.DrawableRes
import com.example.intelicasamobile.R

enum class RoomType(
    @DrawableRes val imageResourceId: Int,
    val apiName: String = ""
) {
    // TODO: Add missing room types
    LIVINGROOM(R.drawable.livingroom),
    KITCHEN(R.drawable.kitchen, "Cocina"),
    BATHROOM(R.drawable.bathroom),
    GARDEN(R.drawable.garden, "Patio"),
    BEDROOM(R.drawable.bedroom),
    OTHER(R.drawable.other);

    companion object {
        fun getRoomType(apiName: String): RoomType {
            return values().find { it.apiName == apiName } ?: OTHER
        }
    }
}