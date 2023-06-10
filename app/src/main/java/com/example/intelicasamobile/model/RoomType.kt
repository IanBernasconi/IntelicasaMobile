package com.example.intelicasamobile.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.intelicasamobile.R

enum class RoomType(
    @DrawableRes val imageResourceId: Int,
    @StringRes val nameResId: Int
) {
    LIVINGROOM(R.drawable.livingroom, R.string.livingroom),
    KITCHEN(R.drawable.kitchen, R.string.kitchen),
    BATHROOM(R.drawable.bathroom, R.string.bathroom),
    GARDEN(R.drawable.garden, R.string.garden),
    BEDROOM(R.drawable.bedroom, R.string.bedroom),
    OTHER(R.drawable.other, R.string.other)
}