package com.example.starsign.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MonsterDao:CardDao<DatabaseMonster> {
    @Query("SELECT * FROM Monster")
    fun getCards() : List<DatabaseMonster>
    @Query("SELECT * FROM Monster WHERE title=:title")
    fun getCard(title: String): DatabaseMonster?
}