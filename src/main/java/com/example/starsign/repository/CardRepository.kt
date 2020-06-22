package com.example.starsign.repository

import com.example.starsign.database.Card
import com.example.starsign.database.StarsignDatabase
import com.example.starsign.database.User

class CardRepository(): ICardRepository {
    override suspend fun addCard(card: Card) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getCards(cards: List<Card>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getCard(card: Card) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun removeCards(cards: List<Card>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}