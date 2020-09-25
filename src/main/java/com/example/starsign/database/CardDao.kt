package com.example.starsign.database

import androidx.room.*

interface CardDao<T:DatabaseCard> {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(card: T)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cards: List<T>)
    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(card: T)
}