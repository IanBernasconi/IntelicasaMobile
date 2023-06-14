package com.example.intelicasamobile.model

data class Action(
    val action: ActionTypes,
    val deviceId: String,
    val params: List<String>
)

enum class ActionTypes(
    val apiName: String,
) {
    TURN_ON("turnOn"),
    TURN_OFF("turnOff"),
    SET_TEMPERATURE("setTemperature"),
    SET_MODE("setMode"),
    SET_VERTICAL_SWING("setVerticalSwing"),
    SET_HORIZONTAL_SWING("setHorizontalSwing"),
    SET_FAN_SPEED("setFanSpeed"),
    OPEN("open"),
    CLOSE("close"),
    LOCK("lock"),
    UNLOCK("unlock"),
    SET_COLOR("setColor"),
    SET_BRIGHTNESS("setBrightness"),
    START_CLEANING("start"),
    PAUSE_CLEANING("pause"),
    CHARGE("dock"),
    SET_LOCATION("setLocation"),
    SET_HEAT_MODE("setHeat"),
    SET_GRILL_MODE("setGrill"),
    SET_CONVECTION_MODE("setConvection");

    companion object {
        fun getActionType(apiName: String): ActionTypes? {
            return values().find { it.apiName == apiName }
        }
    }
}


