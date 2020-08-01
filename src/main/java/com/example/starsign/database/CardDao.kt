package com.example.starsign.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(card: DatabaseCard)
    @Query("SELECT * FROM Card")
    fun getCards() : List<DatabaseCard>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cards: List<DatabaseCard>)
    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(card: DatabaseCard)
    @Query("SELECT * FROM Card WHERE title=:title")
    fun getCard(title: String): DatabaseCard?
    @Query("DELETE FROM Card WHERE cardid in (:cardids)")
    fun deleteCards(cardids: List<Long>)
}