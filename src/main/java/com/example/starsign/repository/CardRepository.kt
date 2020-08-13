package com.example.starsign.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.starsign.database.*
import com.example.starsign.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Challenge
import okhttp3.ResponseBody
import retrofit2.Response

class CardRepository(private val database: StarsignDatabase): ICardRepository {

    override suspend fun addCard(card: Card): NetworkCard {
        return withContext(Dispatchers.IO) {
            val insertProgress = Network.cardApiService.addCard(card)
            try {
                val brandedCard = insertProgress.await()
                database.cardDao.insert(brandedCard.asDatabaseModel())
                brandedCard
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun refreshCards(){
        withContext(Dispatchers.IO){
            val cards = Network.cardApiService.getCards().await()
            database.cardDao.insertAll(cards.asDatabaseModel())
        }
    }

    override suspend fun getCardOnDetail(title: String): NetworkCard? {
        return withContext(Dispatchers.IO) {
            val dbCard = database.cardDao.getCard(title)
            dbCard?.asNetworkModel()
        }
    }

    override suspend fun getDomainCards(): LiveData<List<Card>> {
        return withContext(Dispatchers.IO) {
            MutableLiveData<List<Card>>(database.cardDao.getCards().asDomainModel())
        }
    }

    override suspend fun editCard(card: NetworkCard): Response<Any> {
        return withContext(Dispatchers.IO){
            try {
                val result = Network.cardApiService.updateCard(card).await()
                database.cardDao.update(card.asDatabaseModel())
                result
            } catch(e:Exception){
                throw e
            }
        }
    }
}