package com.example.starsign.repository

import com.example.starsign.database.Card
import com.example.starsign.database.User

interface ICardRepository {
    suspend fun addCard(card: Card)
    suspend fun getCards(cards: List<Card>)
    suspend fun getCard(card: Card)
    suspend fun removeCards(cards: List<Card>)
}