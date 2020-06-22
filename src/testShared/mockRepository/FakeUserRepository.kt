package com.example.starsign.mockRepository

import JwtPayload
import JwtTokenHeader
import com.example.starsign.data.Resulting
import com.example.starsign.network.JwtRequest
import com.example.starsign.network.JwtResponse
import com.example.starsign.network.UserDto
import com.example.starsign.repository.IUserRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.NullPointerException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.NoSuchElementException

class FakeUserRepository: IUserRepository {
    private val users = mutableListOf(JwtPayload("Nedl","Globoesporte", (LocalDateTime.now().plusHours(1L).toEpochSecond(ZoneOffset.UTC))/1000L), JwtPayload("Record", "Imagen3", (LocalDateTime.now().plusHours(1L).toEpochSecond(ZoneOffset.UTC))/1000L))
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter = moshi.adapter(JwtPayload::class.java)
    private val headerAdapter = moshi.adapter(JwtTokenHeader::class.java)
    private val jth = JwtTokenHeader("HS256","JWT")

    @ExperimentalStdlibApi
    override suspend fun addUser(user: UserDto): Resulting<JwtResponse> {
        return if((user.matchingPassword==user.password)&&(!users.map { it.username }.contains(user.username))) {
            val jru = JwtPayload(user.username, user.password, (LocalDateTime.now().plusHours(1L).toEpochSecond(ZoneOffset.UTC))/1000L)
            users.add(jru)
            Resulting.Success(JwtResponse(JwtEncoder.encode(headerAdapter.toJson(jth))+'.'+JwtEncoder.encode(jsonAdapter.toJson(jru))))
        } else{
            Resulting.Error(IllegalArgumentException("Username and password must be filled in and passwords have to match"))
        }
    }

    @ExperimentalStdlibApi
    override suspend fun loginUser(request: JwtRequest): Resulting<JwtResponse> {
        try {
            if (users.map { JwtRequest(request.username, request.password) }.contains(request)) {
                return Resulting.Success(
                    JwtResponse(
                        JwtEncoder.encode(headerAdapter.toJson(jth)) + '.' + JwtEncoder.encode(
                            jsonAdapter.toJson(users.first { it.username == request.username })
                        )
                    )
                )
            } else {
                return Resulting.Error(NullPointerException("Request not found"))
            }
        } catch(e: NoSuchElementException){
            return Resulting.Error(NullPointerException("Request not found"))
        }
    }
}