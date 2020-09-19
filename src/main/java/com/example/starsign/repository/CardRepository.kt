package com.example.starsign.repository

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.room.Database
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
                when(brandedCard){
                    is NetworkMonster -> database.monsterDao.insert(brandedCard.asDatabaseModel() as DatabaseMonster)
                    is NetworkMagic -> database.magicDao.insert(brandedCard.asDatabaseModel() as DatabaseMagic)
                    is NetworkSource -> database.sourceDao.insert(brandedCard.asDatabaseModel() as DatabaseSource)
                }
                brandedCard
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun refreshCards(){
        withContext(Dispatchers.IO){
            val cards = Network.cardApiService.getCards().await()
            cards.forEach { nc ->
                when(nc){
                    is NetworkMonster -> database.monsterDao.insert(nc.asDatabaseModel() as DatabaseMonster)
                    is NetworkMagic -> database.magicDao.insert(nc.asDatabaseModel() as DatabaseMagic)
                    is NetworkSource -> database.sourceDao.insert(nc.asDatabaseModel() as DatabaseSource)
                }
            }
        }
    }

    override suspend fun getCardOnDetail(title: String): NetworkCard? {
        return withContext(Dispatchers.IO) {
            val dbMonster = database.monsterDao.getCard(title)
            val dbMagic = database.magicDao.getCard(title)
            val dbSource = database.sourceDao.getCard(title)
            if(dbMonster!=null){
                dbMonster.asNetworkModel()
            }
            else if(dbMagic!=null){
                dbMagic.asNetworkModel()
            }
            else if(dbSource!=null){
                dbSource.asNetworkModel()
            }
            else{
                throw Resources.NotFoundException("The card was not found")
            }
        }
    }

    override suspend fun getDomainCards(): LiveData<List<Card>> {
        return withContext(Dispatchers.IO) {
            MutableLiveData(listOf(database.monsterDao.getCards().asDomainModel(), database.magicDao.getCards().asDomainModel(), database.sourceDao.getCards().asDomainModel()).flatten())
        }
    }

    override suspend fun editCard(card: NetworkCard): Response<Any> {
        return withContext(Dispatchers.IO){
            try {
                val result = Network.cardApiService.updateCard(card).await()
                val dbMonster = database.monsterDao.getCard(card.title)
                val dbMagic = database.magicDao.getCard(card.title)
                val dbSource = database.sourceDao.getCard(card.title)
                if(dbMonster!=null){
                    database.monsterDao.update(card.asDatabaseModel() as DatabaseMonster)
                }
                else if(dbMagic!=null){
                    database.magicDao.update(card.asDatabaseModel() as DatabaseMagic)
                }
                else if(dbSource!=null){
                    database.sourceDao.update(card.asDatabaseModel() as DatabaseSource)
                }
                else{
                    throw Resources.NotFoundException("The card was not found")
                }
                result
            } catch(e:Exception){
                throw e
            }
        }
    }
}