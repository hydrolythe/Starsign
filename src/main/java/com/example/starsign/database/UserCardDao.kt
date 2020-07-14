package com.example.starsign.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserCardDao {
    @Insert
    fun addCardToUser(userCard: UserCard)
}