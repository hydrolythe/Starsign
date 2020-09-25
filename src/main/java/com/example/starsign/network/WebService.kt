package com.example.starsign.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ClassCastException
import java.lang.reflect.Type

private const val BASE_URL = "http://192.168.137.1:8080/"
val ndeser = NetworkCardDeserializer()
private val gson: Gson = GsonBuilder().registerTypeAdapter(NetworkCard::class.java, ndeser).create()
private val retrofit =
    Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory()).baseUrl(BASE_URL).build()
object Network {
    val userApiService : UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
    val cardApiService : CardApiService by lazy{
        retrofit.create(CardApiService::class.java)
    }
}