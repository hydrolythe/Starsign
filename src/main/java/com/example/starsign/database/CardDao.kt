package com.example.starsign.database

import androidx.room.Insert
import androidx.room.Query

interface CardDao {
    @Insert
    fun insert(card: Card)
}