package com.example.starsign.database

import androidx.room.Insert
import androidx.room.Query

interface UserCardDao {
    @Insert
    fun addCardToUser(userCard: UserCard)
}