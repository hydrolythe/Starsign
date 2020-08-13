package com.example.starsign.repository

import com.example.starsign.data.Resulting
import com.example.starsign.network.JwtRequest
import com.example.starsign.network.JwtResponse
import com.example.starsign.network.Network
import com.example.starsign.network.UserDto

class UserRepository : IUserRepository {
    override suspend fun addUser(user: UserDto): Resulting<JwtResponse> {
        val postUserDeferred = Network.userApiService.register(user)
        try{
            val emp = postUserDeferred.await()
            return Resulting.Success(emp)
        }catch(e:Exception){
            return Resulting.Error(e)
        }
    }

    override suspend fun loginUser(request: JwtRequest): Resulting<JwtResponse> {
        val getTokenDeferred = Network.userApiService.login(request)
        try{
            val emp = getTokenDeferred.await()
            return Resulting.Success(emp)
        }catch(e:Exception){
            return Resulting.Error(e)
        }
    }
}