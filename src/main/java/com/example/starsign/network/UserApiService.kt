package com.example.starsign.network

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService{
    @POST("/login")
    fun login(@Body request: JwtRequest): Deferred<JwtResponse>

    @POST("/register")
    fun register(@Body userDto: UserDto): Deferred<JwtResponse>
}