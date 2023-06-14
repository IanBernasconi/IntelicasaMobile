package com.example.intelicasamobile.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.intelicasamobile.R

enum class ACMode(
    @DrawableRes val imageResourceId: Int,
    val value : String,
    @StringRes val nameResId: Int
) {
    FAN(R.drawable.acventilationmode, "fan", R.string.ACM_fan),
    COOL(R.drawable.accoldmode, "cool", R.string.ACM_cool),
    HEAT(R.drawable.acheatmode, "heat", R.string.ACM_heat);

    companion object {
        fun getMode(apiName: String): ACMode {
            return values().find { it.value == apiName } ?: FAN
        }
    }
}

enum class OvenHeatMode(
    @DrawableRes val imageResourceId: Int,
    val value : String,
    @StringRes val nameResId: Int
) {
    CONVENTIONAL(R.drawable.heatmodeconventional, "conventional", nameResId = R.string.OHM_conventional),
    TOP(R.drawable.heatmodetop, "top", nameResId = R.string.OHM_top),
    BOTTOM(R.drawable.heatmodebottom, "bottom", nameResId = R.string.OHM_bottom);

    companion object {
        fun getMode(apiName: String): OvenHeatMode {
            return values().find { it.value == apiName } ?: CONVENTIONAL
        }
    }

}

enum class OvenGrillMode(
    @DrawableRes val imageResourceId: Int,
    val value : String,
    @StringRes val nameResId: Int
) {
    OFF(R.drawable.grillmodeoff, "off", nameResId = R.string.OGM_off),
    ECO(R.drawable.ecomode, "eco", nameResId = R.string.OGM_eco),
    CONVENTIONAL(R.drawable.grillmodeconventional, "large", nameResId = R.string.OGM_full);

    companion object {
        fun getMode(apiName: String): OvenGrillMode {
            return values().find { it.value == apiName } ?: OFF
        }
    }
}

enum class OvenConvectionMode(
    @DrawableRes val imageResourceId: Int,
    val value : String,
    @StringRes val nameResId: Int
) {
    OFF(R.drawable.convectionmodeoff, "off", nameResId = R.string.OCM_off),
    ECO(R.drawable.ecomode, "eco", nameResId = R.string.OCM_eco),
    CONVENTIONAL(R.drawable.convectionmodenormal, "normal", nameResId = R.string.OCM_full);

    companion object {
        fun getMode(apiName: String): OvenConvectionMode {
            return values().find { it.value == apiName } ?: OFF
        }
    }
}

enum class VacuumCleanMode(
    @DrawableRes val imageResourceId: Int,
    val value : String,
    @StringRes val nameResId: Int
) {
    MOP(R.drawable.mopmode, "mop", nameResId = R.string.VCM_mop),
    VACUUM(R.drawable.vacuummode, "vacuum", nameResId = R.string.VCM_vacuum);

    companion object {
        fun getMode(apiName: String): VacuumCleanMode {
            return values().find { it.value == apiName } ?: VACUUM
        }
    }
}