package com.example.intelicasamobile.model

import androidx.annotation.StringRes
import com.example.intelicasamobile.R

open class Device(
    val deviceType: DeviceTypes,
    @StringRes val name: Int,
    val meta: Meta = Meta(),
)

class ACDevice(val state: ACState = ACState()) : Device(
    DeviceTypes.AIR_CONDITIONER,
    R.string.air_conditioner,
    meta = Meta(category = DeviceTypes.AIR_CONDITIONER),
)

class LightDevice(val state: LightState = LightState()) : Device(
    deviceType = DeviceTypes.LAMP,
    name = R.string.lamp,
    meta = Meta(category = DeviceTypes.LAMP),
)

class OvenDevice(val state: OvenState = OvenState()) : Device(
    deviceType = DeviceTypes.OVEN,
    name = R.string.oven,
    meta = Meta(category = DeviceTypes.OVEN),
)

class DoorDevice(val state: DoorState = DoorState()) : Device(
    deviceType = DeviceTypes.DOOR,
    name = R.string.door,
    meta = Meta(category = DeviceTypes.DOOR),
)

class VacuumDevice(val state: VacuumState = VacuumState()) : Device(
    deviceType = DeviceTypes.VACUUM_CLEANER,
    name = R.string.vacuum_cleaner,
    meta = Meta(category = DeviceTypes.VACUUM_CLEANER),
)