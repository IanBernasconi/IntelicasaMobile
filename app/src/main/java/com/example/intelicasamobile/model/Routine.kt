package com.example.intelicasamobile.model

data class Routine(
    val id: String,
    val name: String,
    val actions: List<Action<*>>,
)

data class Action<T>(
    val action: ActionTypes,
    val deviceId: String,
    val params: List<T>
)

enum class ActionTypes(
    val apiName: String,
    val paramType: Class<*>
) {
    /* TODO check */
    TURN_ON("turn_on", Boolean::class.java),
    TURN_OFF("turn_off", Boolean::class.java),
    SET_TEMPERATURE("set_temperature", Int::class.java),
    SET_MODE("set_mode", String::class.java),
    SET_FAN_SPEED("set_fan_speed", String::class.java),
    START_CLEANING("start_cleaning", Boolean::class.java),
    STOP_CLEANING("stop_cleaning", Boolean::class.java),
    OPEN("open", Boolean::class.java),
    CLOSE("close", Boolean::class.java),
    SET_INTENSITY("set_intensity", Int::class.java),
    SET_COLOR("set_color", String::class.java),
    SET_BRIGHTNESS("set_brightness", Int::class.java),
    SET_VOLUME("set_volume", Int::class.java),
    SET_CHANNEL("set_channel", Int::class.java),
    SET_POSITION("set_position", Int::class.java),
    SET_MEDIA("set_media", String::class.java),
    SET_PLAYBACK("set_playback", String::class.java),
    SET_SOURCE("set_source", String::class.java),
    SET_INPUT("set_input", String::class.java),
    SET_POWER("set_power", Boolean::class.java),
    SET_STATE("set_state", Boolean::class.java),
    SET_LOCK("set_lock", Boolean::class.java),
    SET_ALARM("set_alarm", Boolean::class.java),
    SET_TIMER("set_timer", Boolean::class.java),
    SET_TIME("set_time", Boolean::class.java),
    SET_DATE("set_date", Boolean::class.java),
    SET_TIMEZONE("set_timezone", Boolean::class.java),
    SET_LANGUAGE("set_language", Boolean::class.java),
    SET_VOLUME_LEVEL("set_volume_level", Int::class.java),
    SET_BRIGHTNESS_LEVEL("set_brightness_level", Int::class.java),
    SET_CHANNEL_LEVEL("set_channel_level", Int::class.java),
    SET_POSITION_LEVEL("set_position_level", Int::class.java),
    SET_MEDIA_LEVEL("set_media_level", Int::class.java),
    SET_PLAYBACK_LEVEL("set_playback_level", Int::class.java),
    SET_SOURCE_LEVEL("set_source_level", Int::class.java),
    SET_INPUT_LEVEL("set_input_level", Int::class.java),
    SET_POWER_LEVEL("set_power_level", Int::class.java),
    SET_STATE_LEVEL("set_state_level", Int::class.java)
}


