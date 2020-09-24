package com.example.starsign.network

import com.example.starsign.database.Card
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface CardApiService {
    @POST("card")
    fun<T: NetworkCard> addCard(@Body card: Card): Deferred<JSONObject>
    @GET("card")
    fun<T: NetworkCard> getCards(): Deferred<List<JSONObject>>
    @PUT("card")
    fun updateCard(@Body card: NetworkCard):Deferred<Response<Any>>
    @DELETE("card")
    fun deleteCards(@QueryName title: String):Deferred<NetworkCard>
}