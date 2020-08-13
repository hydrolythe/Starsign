package com.example.starsign.repository

import androidx.lifecycle.LiveData
import com.example.starsign.database.Card
import com.example.starsign.database.DatabaseCard
import com.example.starsign.database.NetworkCard
import com.example.starsign.database.User
import retrofit2.Response
import okhttp3.ResponseBody

interface ICardRepository {
    suspend fun addCard(card: Card): NetworkCard
    suspend fun editCard(card: NetworkCard): Response<Any>
    suspend fun refreshCards()
    suspend fun getCardOnDetail(title: String): NetworkCard?
    suspend fun getDomainCards(): LiveData<List<Card>>
}