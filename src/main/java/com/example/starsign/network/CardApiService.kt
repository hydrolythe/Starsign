package com.example.starsign.network

import com.example.starsign.database.Card
import com.example.starsign.database.DatabaseCard
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface CardApiService {
    @POST("Card")
    fun addCard(@Body card: Card): Deferred<DatabaseCard>
    @GET("Card")
    fun getCards(): Deferred<List<DatabaseCard>>
    @PUT("Card")
    fun updateCard(@Body card: DatabaseCard):Deferred<Response<Any>>
    @DELETE("Card")
    fun deleteCards(@Body titles: List<String>):Deferred<List<DatabaseCard>>
}