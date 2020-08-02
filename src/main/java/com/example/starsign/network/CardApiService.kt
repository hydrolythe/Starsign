package com.example.starsign.network

import com.example.starsign.database.Card
import com.example.starsign.database.DatabaseCard
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface CardApiService {
    @POST("card")
    fun addCard(@Body card: Card): Deferred<DatabaseCard>
    @GET("card")
    fun getCards(): Deferred<List<DatabaseCard>>
    @PUT("card")
    fun updateCard(@Body card: DatabaseCard):Deferred<Response<Any>>
    @DELETE("card")
    fun deleteCards(@Body titles: List<String>):Deferred<List<DatabaseCard>>
}