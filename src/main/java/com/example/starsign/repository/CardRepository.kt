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

    val cards = MutableLiveData<List<Card>>(database.cardDao.getCards().asDomainModel())

    val lcards : LiveData<List<Card>>
    get() = cards

    override suspend fun addCard(card: Card): DatabaseCard {
        return withContext(Dispatchers.IO) {
            val insertProgress = Network.cardApiService.addCard(card)
            try {
                val brandedCard = insertProgress.await()
                database.cardDao.insert(brandedCard)
                brandedCard
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun refreshCards(){
        withContext(Dispatchers.IO){
            val cards = Network.cardApiService.getCards().await()
            database.cardDao.insertAll(cards)
        }
    }

    override fun getCardOnDetail(title: String): DatabaseCard? {
        return database.cardDao.getCard(title)
    }

    override fun getDomainCards(): LiveData<List<Card>> {
        return lcards
    }

    override suspend fun removeCards(cards: List<Card>):List<DatabaseCard> {
        return withContext(Dispatchers.IO){
            try{
                val result = Network.cardApiService.deleteCards(cards.map{it.title}).await()
                database.cardDao.deleteCards(result.map{it.cardid})
                result
            } catch(e:Exception){
                throw e
            }
        }
    }

    override suspend fun editCard(card: DatabaseCard): Response<Any> {
        return withContext(Dispatchers.IO){
            try {
                val result = Network.cardApiService.updateCard(card).await()
                database.cardDao.update(card)
                result
            } catch(e:Exception){
                throw e
            }
        }
    }
}