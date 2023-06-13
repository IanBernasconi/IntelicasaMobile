package com.example.intelicasamobile.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRoutineList (

    @SerializedName("result" ) var result : List<NetworkRoutinesResult> = listOf()

)

data class NetworkRoom (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)

data class Device (

    @SerializedName("id"   ) var id   : String,
    @SerializedName("name" ) var name : String,
    @SerializedName("type" ) var type : NetworkType?   = NetworkType(),
    @SerializedName("room" ) var room : NetworkRoom?   = NetworkRoom(),
    @SerializedName("meta" ) var meta : NetworkMeta?   = NetworkMeta()

)

data class NetworkAction (

    @SerializedName("device"     ) var device     : Device,
    @SerializedName("actionName" ) var actionName : String,
    @SerializedName("params"     ) var params     : List<String> = listOf()

)

data class NetworkRoutinesResult (

    @SerializedName("id"      ) var id      : String,
    @SerializedName("name"    ) var name    : String,
    @SerializedName("actions" ) var actions : List<NetworkAction> = listOf(),
    @SerializedName("meta"    ) var meta    : NetworkMeta?              = NetworkMeta()

)