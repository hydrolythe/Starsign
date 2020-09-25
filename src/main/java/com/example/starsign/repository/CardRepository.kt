package com.example.starsign.repository

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.starsign.database.*
import com.example.starsign.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CardRepository(private val database: StarsignDatabase): ICardRepository {

    override suspend fun addCard(card: Card): NetworkCard {
        return withContext(Dispatchers.IO) {
            try {
                when(card){
                    is Monster -> {
                        val nmonster = Network.cardApiService.addCard(card).await()
                        database.monsterDao.insert(nmonster.asDatabaseModel() as DatabaseMonster)
                        nmonster
                    }
                    is Magic -> {
                        val nmagic = Network.cardApiService.addCard(card).await()
                        database.magicDao.insert(nmagic.asDatabaseModel() as DatabaseMagic)
                        nmagic
                    }
                    is Source -> {
                        val nsource = Network.cardApiService.addCard(card).await()
                        database.sourceDao.insert(nsource.asDatabaseModel() as DatabaseSource)
                        nsource
                    }
                    else -> throw IllegalArgumentException("Not found")
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun refreshCards(){
        withContext(Dispatchers.IO){
            val ncards = Network.cardApiService.getCards().await()
            val nmonsters = ncards.filterIsInstance<NetworkMonster>()
            val nspells = ncards.filterIsInstance<NetworkMagic>()
            val nsources = ncards.filterIsInstance<NetworkSource>()
            database.monsterDao.insertAll(nmonsters.asDatabaseModel())
            database.magicDao.insertAll(nspells.asDatabaseModel())
            database.sourceDao.insertAll(nsources.asDatabaseModel())
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
            val flatList = listOf(database.monsterDao.getCards().asDomainModel(), database.magicDao.getCards().asDomainModel(), database.sourceDao.getCards().asDomainModel()).flatten()
            MutableLiveData<List<Card>>(flatList)
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