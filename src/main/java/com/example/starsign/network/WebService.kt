package com.example.starsign.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "localhost:8080"
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
object Network {
    private val retrofit =
        Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).baseUrl(BASE_URL).build()
    val userApiService = retrofit.create(UserApiService::class.java)
    val cardApiService = retrofit.create(CardApiService::class.java)
}