package com.example.starsign.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SourceDao:CardDao<DatabaseSource> {
    @Query("SELECT * FROM Source")
    fun getCards() : List<DatabaseSource>
    @Query("SELECT * FROM Source WHERE title=:title")
    fun getCard(title: String): DatabaseSource?
}