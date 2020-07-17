package com.example.starsign.repository

import com.example.starsign.database.Card
import com.example.starsign.database.DatabaseCard
import com.example.starsign.database.User
import retrofit2.Response
import okhttp3.ResponseBody

interface ICardRepository {
    suspend fun addCard(card: Card): DatabaseCard
    suspend fun removeCards(cards: List<Card>):List<DatabaseCard>
    suspend fun editCard(card: DatabaseCard): Response<Any>
    suspend fun refreshCards()
    fun getCardOnDetail(title: String): DatabaseCard?
    fun getDomainCards():List<Card>?
}