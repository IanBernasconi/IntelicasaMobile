package com.example.intelicasamobile.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkEvents(
    @SerializedName("timestamp") var timestamp: String,
    @SerializedName("deviceId") var deviceId: String,
    @SerializedName("event") var event: String,
    @SerializedName("args") var value: Map<String, String> = mapOf()
)