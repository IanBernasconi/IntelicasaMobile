package com.example.intelicasamobile.data.network.model
import com.google.gson.annotations.SerializedName


data class NetworkDeviceList (

    @SerializedName("result" ) var result : List<NetworkDevicesResult> = listOf()

)

data class NetworkType (

    @SerializedName("id"         ) var id         : String? = null,
    @SerializedName("name"       ) var name       : String? = null,
    @SerializedName("powerUsage" ) var powerUsage : Int?    = null

)

data class NetworkState (

    @SerializedName("status"       ) var status        : String? = null,
    @SerializedName("temperature"  ) var temperature   : Float?    = null,
    @SerializedName("heat"         ) var heat          : String? = null,
    @SerializedName("grill"        ) var grill         : String? = null,
    @SerializedName("convection"   ) var convection    : String? = null,
    @SerializedName("brightness"   ) var brightness    : String? = null,
    @SerializedName("color"        ) var color         : String? = null,
    @SerializedName("mode"         ) var mode          : String? = null,
    @SerializedName("fanSpeed"     ) var fanSpeed      : String? = null,
    @SerializedName("verticalSwing") var verticalSwing : String? = null,
    @SerializedName("horizontalSwing") var horizontalSwing : String? = null,
    @SerializedName("lock"         ) var locked          : String? = null,
    @SerializedName("batteryLevel" ) var batteryLevel : Int? = null,
    @SerializedName("location"     ) var location: NetworkRoom? = null

)
data class NetworkCategory (

    @SerializedName("id"    ) var id    : String? = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("img"   ) var img   : String? = null,
    @SerializedName("value" ) var value : String? = null

)

data class NetworkDevicesResult (

    @SerializedName("id"    ) var id    : String,
    @SerializedName("name"  ) var name  : String,
    @SerializedName("type"  ) var networkType  : NetworkType?   = NetworkType(),
    @SerializedName("state" ) var networkState : NetworkState?  = NetworkState(),
    @SerializedName("meta"  ) var meta  : NetworkMeta?   = NetworkMeta(),
    @SerializedName("room"  ) var room  : NetworkRoom?   = NetworkRoom()

)

data class NetworkMeta (

    @SerializedName("favorite" ) var favorite : Boolean?  = null,
    @SerializedName("category" ) var category : NetworkCategory? = NetworkCategory()

)