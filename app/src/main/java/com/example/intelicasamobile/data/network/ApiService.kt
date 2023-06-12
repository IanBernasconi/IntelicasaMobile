package com.example.intelicasamobile.data.network

import com.example.intelicasamobile.data.network.model.NetworkDeviceList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/devices")
    suspend fun getDevices(): Response<NetworkDeviceList>

}