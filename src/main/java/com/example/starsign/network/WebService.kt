package com.example.starsign.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.ClassCastException
import java.lang.reflect.Type

private const val BASE_URL = "http://192.168.1.160:8080/"
private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).add(
    PolymorphicJsonAdapterFactory.of(NetworkCard::class.java,"type")
    .withSubtype(NetworkMonster::class.java,"Monster")
    .withSubtype(NetworkMagic::class.java,"Magic")
    .withSubtype(NetworkSource::class.java,"Source")
).build()
private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory()).baseUrl(BASE_URL).build()
object Network {
    val userApiService : UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
    val cardApiService : CardApiService by lazy{
        retrofit.create(CardApiService::class.java)
    }
}