package com.example.starsign.network

import com.example.starsign.database.Card
import com.example.starsign.database.DatabaseCard
import com.example.starsign.database.DatabaseMonster
import com.example.starsign.database.NetworkCard
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface CardApiService {
    @POST("card")
    fun addCard(@Body card: Card): Deferred<NetworkCard>
    @GET("card")
    fun getCards(): Deferred<List<NetworkCard>>
    @PUT("card")
    fun updateCard(@Body card: NetworkCard):Deferred<Response<Any>>
    @DELETE("card")
    fun deleteCards(@QueryName title: String):Deferred<NetworkCard>
}