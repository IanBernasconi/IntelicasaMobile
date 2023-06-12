package com.example.intelicasamobile.data.network

import com.example.intelicasamobile.data.network.model.NetworkDeviceList
import com.example.intelicasamobile.data.network.model.NetworkRoutineList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/devices")
    suspend fun getDevices(): Response<NetworkDeviceList>

    @GET("/api/routines")
    suspend fun getRoutines(): Response<NetworkRoutineList>

}