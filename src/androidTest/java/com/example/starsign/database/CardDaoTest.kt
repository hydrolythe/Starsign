package com.example.starsign.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CardDaoTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: StarsignDatabase

    @Before
    fun initDb(){
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), StarsignDatabase::class.java).build()
    }

    @Test
    fun insertCard_getCardByTitle()= runBlockingTest{
        val databaseSource = DatabaseSource(1, "xenon", mapOf(Pair(Mana.APEIRON, 1)))
        database.cardDao.insert(databaseSource)
        val loadedCard = database.cardDao.getCard(databaseSource.title)
        assertThat<DatabaseCard>(loadedCard as DatabaseCard, notNullValue())
        assertThat(loadedCard.cardid, `is`(databaseSource.cardid))
        assertThat(loadedCard.title, `is`(databaseSource.title))
    }

    @Test
    fun insertCards_getCards() = runBlockingTest{
        val databasecards = listOf(DatabaseSource(1, "xenon", mapOf(Pair(Mana.APEIRON, 1))), DatabaseMagic(2, "Neohell", SpellSpecies.FIELD, mapOf(Pair(Spell.DAMAGE, 3)), mapOf(Pair(Mana.SOUL, 2))))
        database.cardDao.insertAll(databasecards)
        val loadedCards = database.cardDao.getCards()
        assertThat<List<DatabaseCard>>(loadedCards, notNullValue())
        assertThat(loadedCards.size, `is`(databasecards.size))
    }

    @Test
    fun updateCard_updatesCard() = runBlockingTest{
        val databaseSource = DatabaseSource(1, "xenon", mapOf(Pair(Mana.APEIRON, 1)))
        database.cardDao.insert(databaseSource)
        val correctedDatabaseSource = DatabaseSource(1, "neon", mapOf(Pair(Mana.VOID, 2)))
        database.cardDao.update(correctedDatabaseSource)
        val loadedCard = database.cardDao.getCard(correctedDatabaseSource.title)
        assertThat<DatabaseCard>(loadedCard as DatabaseCard, notNullValue())
        assertThat(loadedCard.cardid, `is`(correctedDatabaseSource.cardid))
        assertThat(loadedCard.title, `is`(correctedDatabaseSource.title))
    }

    @After
    fun closeDb() = database.close()
}