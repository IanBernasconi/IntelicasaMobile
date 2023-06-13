package com.example.intelicasamobile.data.network.model

import com.google.gson.annotations.SerializedName


data class NetworkRoomsList (

    @SerializedName("result" ) var result : ArrayList<NetworkRoomsResult> = arrayListOf()

)

data class NetworkRoomsResult (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("meta" ) var meta : NetworkRoomMeta?   = NetworkRoomMeta()

)

data class NetworkRoomMeta (

    @SerializedName("type" ) var type : String? = null

)
