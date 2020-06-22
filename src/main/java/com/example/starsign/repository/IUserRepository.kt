package com.example.starsign.repository

import com.example.starsign.data.Resulting
import com.example.starsign.network.JwtRequest
import com.example.starsign.network.JwtResponse
import com.example.starsign.network.UserDto

interface IUserRepository {
    suspend fun addUser(user: UserDto): Resulting<JwtResponse>
    suspend fun loginUser(request: JwtRequest): Resulting<JwtResponse>
}