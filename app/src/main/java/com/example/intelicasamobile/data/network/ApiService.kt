package com.example.intelicasamobile.data.network

import com.example.intelicasamobile.data.network.model.NetworkDevice
import com.example.intelicasamobile.data.network.model.NetworkDeviceList
import com.example.intelicasamobile.data.network.model.NetworkRoomsList
import com.example.intelicasamobile.data.network.model.NetworkRoutineList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/api/devices")
    suspend fun getDevices(): Response<NetworkDeviceList>

    @GET("/api/devices/{id}")
    suspend fun getDevice(@Path("id") id: String): Response<NetworkDevice>

    @GET("/api/routines")
    suspend fun getRoutines(): Response<NetworkRoutineList>

    @PUT("/api/devices/{id}/{actionName}")
    suspend fun triggerEvent(@Path("id") id: String, @Path("actionName") actionName: String, @Body params: List<String>)

    @PUT("/api/routines/{id}/execute")
    suspend fun executeRoutine(@Path("id") id: String): Response<Unit>

    @GET("/api/rooms")
    suspend fun getRooms(): Response<NetworkRoomsList>

    @GET("/api/rooms/{id}/devices")
    suspend fun getDevicesByRoom(@Path("id") id: String): Response<NetworkDeviceList>

}