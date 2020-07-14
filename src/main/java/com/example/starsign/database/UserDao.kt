package com.example.starsign.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert(u: User)
    @Query("SELECT * FROM Card c WHERE c.title = (SELECT * FROM Cards_Per_User p WHERE p.user = :u)")
    fun cardsOfUser(u: User):List<Card>
}