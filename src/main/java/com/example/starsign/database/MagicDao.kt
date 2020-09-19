package com.example.starsign.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MagicDao:CardDao<DatabaseMagic> {
    @Query("SELECT * FROM Magic")
    fun getCards() : List<DatabaseMagic>
    @Query("SELECT * FROM Magic WHERE title=:title")
    fun getCard(title: String): DatabaseMagic?
}