package com.example.intelicasamobile.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.example.intelicasamobile.R

enum class ACMode(
    @DrawableRes val imageResourceId: Int,
    val value : String,
    @StringRes val nameResId: Int
) {
    FAN(R.drawable.acventilationmode, "fan", R.string.ACM_fan),
    COOL(R.drawable.accoldmode, "cool", R.string.ACM_cool),
    HEAT(R.drawable.acheatmode, "heat", R.string.ACM_heat);
}

enum class OvenHeatMode(
    @DrawableRes val imageResourceId: Int,
    val value : String,
    @StringRes val nameResId: Int
) {
    CONVENTIONAL(R.drawable.heatmodeconventional, "conventional", nameResId = R.string.OHM_conventional),
    TOP(R.drawable.heatmodetop, "top", nameResId = R.string.OHM_top),
    BOTTOM(R.drawable.heatmodebottom, "bottom", nameResId = R.string.OHM_bottom)
}

enum class OvenGrillMode(
    @DrawableRes val imageResourceId: Int,
    val value : String,
    @StringRes val nameResId: Int
) {
    //TODO CHANGE VALUES

    OFF(R.drawable.grillmodeoff, "grill", nameResId = R.string.OGM_off),
    ECO(R.drawable.ecomode, "grill_eco", nameResId = R.string.OGM_eco),
    CONVENTIONAL(R.drawable.grillmodeconventional, "grill_ventilated", nameResId = R.string.OGM_full)
}

enum class OvenConvectionMode(
    @DrawableRes val imageResourceId: Int,
    val value : String,
    @StringRes val nameResId: Int
) {
    //TODO CHANGE VALUES

    OFF(R.drawable.convectionmodeoff, "convection", nameResId = R.string.OCM_off),
    ECO(R.drawable.ecomode, "convection_eco", nameResId = R.string.OCM_eco),
    CONVENTIONAL(R.drawable.convectionmodenormal, "convection_ventilated", nameResId = R.string.OCM_full)
}