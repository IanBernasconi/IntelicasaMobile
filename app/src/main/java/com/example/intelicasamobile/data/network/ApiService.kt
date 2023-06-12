package com.example.intelicasamobile.data.network

import com.example.intelicasamobile.data.network.model.NetworkDeviceList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/api/devices")
    suspend fun getDevices(): Response<NetworkDeviceList>

    @PUT("/api/devices/{id}/{actionName}")
    suspend fun triggerEvent(@Path("id") id: String, @Path("actionName") actionName: String, @Body params: List<String>)

    @PUT("/api/routines/{id}/execute")
    suspend fun executeRoutine(@Path("id") id: String): Response<Unit>

}